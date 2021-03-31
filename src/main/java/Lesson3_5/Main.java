package Lesson3_5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

//
//    Организуем гонки:
//    Все участники должны стартовать одновременно, несмотря на то, что на подготовку у каждого из них уходит разное время.
//    В туннель не может заехать одновременно больше половины участников (условность).
//    Попробуйте всё это синхронизировать.
//    Только после того как все завершат гонку, нужно выдать объявление об окончании.
//    Можете корректировать классы (в т.ч. конструктор машин) и добавлять объекты классов из пакета util.concurrent.
        public static final int CARS_COUNT = 3;
        public static int stageID = 0;
    public static CountDownLatch cdl;
    public static CyclicBarrier cyclicBarrier = new CyclicBarrier(CARS_COUNT, new StartOrFinish());
    public static Semaphore roadSemaphore = new Semaphore(2);
    public static Semaphore tunnelSemaphore = new Semaphore(2);
    public static ExecutorService roadExecutorService = Executors.newFixedThreadPool(CARS_COUNT);
    public static AtomicInteger winner =  new AtomicInteger(0);
    public static String winnerName;
    public static Race race;

        public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
            race = new Race( new Road(60), new Tunnel(), new Road(40));
            cdl = new CountDownLatch(CARS_COUNT*race.getStages().size());
            Car[] cars = new Car[CARS_COUNT];
            for (int i = 0; i < cars.length; i++) {
                cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
            }


            for (int i = 0; i < cars.length; i++) {
               new Thread(cars[i]).start();
            }
            cdl.await(15000, TimeUnit.MILLISECONDS);
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка завершилась!!!");
            roadExecutorService.shutdown();
            System.exit(0);
        }
    }

    class Car implements Runnable {
        private static int CARS_COUNT;
        static {
            CARS_COUNT = 0;
        }
        private Race race;
        private int speed;
        private String name;
        private Object mon;
        private  boolean inProgress = false;
        private int stageLevel = 1;
        public String getName() {
            return name;
        }
        public int getSpeed() {
            return speed;
        }

        public int getStageLevel() {
            return stageLevel;
        }

        public void setStageLevel(int stageLevel) {
            this.stageLevel = stageLevel;
        }

        public void setInProgress(boolean inProgress) {
            this.inProgress = inProgress;
        }

        public boolean isInProgress() {
            return inProgress;
        }

        public Object getMon() {
            return mon;
        }

        public Car(Race race, int speed) {
            this.race = race;
            this.speed = speed;
            CARS_COUNT++;
            this.name = "Участник #" + CARS_COUNT;
            mon = new Object();
        }
        @Override
        public void run() {
            try {
                System.out.println(this.name + " готовится");
                Thread.sleep(500 + (int)(Math.random() * 800));

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(this.name + " готов");
            try { Main.cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }
        }
    }
     abstract class Stage {
        protected int length;
        protected String description;

        public String getDescription() {
            return description;
        }
        public abstract void go(Car c);
    }
     class Road extends Stage {
    public  int id ;

        public Road(int length) {
            this.length = length;
            this.description = "Дорога " + length + " метров";
            id = Main.stageID+1;
            Main.stageID++;
        }

         public int getId() {
             return id;
         }

         @Override
        public void go(final Car c) {
            Main.roadExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    while (c.getStageLevel() != getId()) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    c.setStageLevel(c.getStageLevel()+1);
                    synchronized (c.getMon()) {
                        c.setInProgress(true);
                        try {
                            Main.roadSemaphore.acquire();
                            System.out.println(c.getName() + " начал этап: " + description);
                            Thread.sleep(length / c.getSpeed() * 1000);
                            System.out.println(c.getName() + " закончил этап: " + description);
                            Main.roadSemaphore.release();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
//                            if (Main.winner.get() == 0 && (c.getStageLevel() == Main.race.getStages().size()))
//                            { Main.winner.incrementAndGet();
//                                System.out.println("--------------------" + c.getName() + " won!");
//                                Main.winnerName = c.getName();}

                            c.setInProgress(false);
                            Main.cdl.countDown();
//                            System.out.println("COUNTDOWN" + Main.cdl.getCount());
                        }
                    }
                }
            });
        }
    }

     class Tunnel extends Stage {
    public  int id;

        public Tunnel() {
            this.length = 80;
            this.description = "Тоннель " + length + " метров";
            id = Main.stageID+1;
            Main.stageID++;
        }
         @Override
        public void go(final Car c) {
            Main.roadExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    while (id!= c.getStageLevel()) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    c.setStageLevel(c.getStageLevel()+1);
                    synchronized (c.getMon()) {
                        try {
                            while (c.isInProgress()) {
//                                    wait();
                                Thread.sleep(1);
                            }
                            Main.tunnelSemaphore.acquire();
                            System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                            System.out.println(c.getName() + " начал этап: " + description);
                            Thread.sleep(length / c.getSpeed() * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {



                            System.out.println(c.getName() + " закончил этап: " + description);
//                            if (Main.winner.get() == 0 && (c.getStageLevel() > Main.race.getStages().size()))
//                            { Main.winner.incrementAndGet();
//                                System.out.println("--------------------" + c.getName() + " won!");
//                                Main.winnerName = c.getName();}
                            Main.cdl.countDown();
//                            System.out.println("COUNTDOWN" + Main.cdl.getCount());

                        }
                    }
                }
            });

        }
    }
     class Race {
        private static ArrayList<Stage> stages;
        public static ArrayList<Stage> getStages() { return stages; }
        public Race( Stage... stages) {
            this.stages = new ArrayList<>(Arrays.asList(stages));
        }
    }
class StartOrFinish implements Runnable {
    private static boolean finish = false;
    @Override
    public void run() {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");


    }
}

