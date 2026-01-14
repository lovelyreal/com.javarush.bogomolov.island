package service;

import Entities.Animal;
import Entities.Plant;
import Util.*;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Task implements Runnable {

    Island island;
    Island.Location[][] locations;
    private int x;
    private int y;

    public Task(int x, int y, Island island) {
        this.island = island;
        locations = island.getLocations();
        this.x = x;
        this.y = y;

    }

    @Override
    public void run() {
        boolean locked = false;

        try {
            //System.out.println("Пытаюсь заблокировать локацию [" + x + "][" + y + "]");

            // Пытаемся получить блокировку
            locked = locations[x][y].reentrantLock.tryLock(100, TimeUnit.MILLISECONDS);

            if (locked) {
                //System.out.println("Локация [" + x + "][" + y + "] успешно заблокирована");

                try {
                    List<Eatable> animals = locations[x][y].getAnimals();
                    //System.out.println("Количество животных в локации: " + animals.size());
                    Iterator<Eatable> iterator = animals.iterator();

                    while (iterator.hasNext()) {
                        Eatable entity = iterator.next();
                        if (entity instanceof Animal) {
                            Animal animal = (Animal) entity;
                            int oldX = animal.getX();
                            int oldY = animal.getY();

                            animal.move();

                            int newX = animal.getX();
                            int newY = animal.getY();
                            // Проверяем, переместилось ли животное
                            if (animal.getX() != oldX || animal.getY() != oldY) {

                                iterator.remove();
                                if (locations[newX][newY].reentrantLock.tryLock(100, TimeUnit.MILLISECONDS)) {
                                    try {
                                        locations[newX][newY].getAnimals().add(animal);
                                    } finally {
                                        locations[newX][newY].reentrantLock.unlock();
                                    }
                                }
                            }
                        }
                    }

                } finally {
                    // Только если мы получили блокировку - разблокируем
                    if (locked) {
                        locations[x][y].reentrantLock.unlock();
                        //System.out.println("Локация [" + x + "][" + y + "] разблокирована");
                    }
                }
            } else {
                System.out.println("Не удалось заблокировать локацию [" + x + "][" + y + "] за 100 мс");
            }

        } catch (InterruptedException e) {
            // Правильная обработка прерывания
            Thread.currentThread().interrupt();
            System.out.println("Поток для локации [" + x + "][" + y + "] был прерван");

        } catch (Exception e) {
            System.err.println("Ошибка в Task [" + x + "][" + y + "]: " + e.getMessage());
            e.printStackTrace();

        }
    }
}

