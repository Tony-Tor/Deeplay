import exceptions.ADijkException;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Solution {

    public static DataMoveCreature dataMoveCreature;
    public static int worldHeight = 4;
    public static int worldWidth = 4;
    public static Point startPoint = new Point(0,0);
    public static Point endPoint = new Point(worldWidth-1, worldHeight-1);

    public static void main(String[] args) {
        dataMoveCreature = new DataMoveCreature("World.json");
        System.out.println(getResult("STWSWTPPTPTTPWPP","Swamper"));
    }

    public static int getResult(String world, String creature){
        if (world.length()!=16)
            throw new ADijkException("String world not equals 16: " + world.length() + ", World: " + world);    //Выбрасывает исключение если строка больше или меньше 16

        Solution solution = new Solution();

        int[][] arr = solution.createWorld(world, creature, worldWidth, worldHeight);       //Создание массива с информацией о мире
        return solution.dAlgorithm(arr, startPoint, endPoint);  //Алгоритм поиска путей Дейкстры
    }

    private int[][] createWorld(String world, String creature, int width, int height) {
        int[][] arr = new int[width][height];
        char[] chars = world.toCharArray(); //Разбивка строки на символы

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                arr[x][y] = dataMoveCreature.getCostMoveFromCell(
                        chars[x+y*width]+"",  //преобразование одномерного массива в двумерный и получение ячейки
                        creature
                );
            }
        }

        return arr;
    }

    //Алгоритм поиска Дейкстры
    private int dAlgorithm(int[][] arr, Point start, Point end){

        //Фронт
        Queue<PointAndCost> front = new PriorityQueue<>(
                Comparator.comparingInt(PointAndCost::getCost)
        );
        front.add(new PointAndCost(start, 0)); //Стартовая точка

        //Посещенные вершины
        Map<Point, PointAndCost> visited= new HashMap<>();
        visited.put(start, new PointAndCost(null, 0)); //Стартовая точка

        while(!front.isEmpty()){
            PointAndCost current = front.poll(); //Получение нода из границы

            if(current.getPoint().equals(end)){
                return current.getCost(); //Возвращает финальную цену пути
            }

            for(Point next: neighbors(current.getPoint())){
                int nCost = visited.get(current.getPoint()).getCost() + arr[next.x][next.y];  //Сумарная цена прохождения нода
                if(!visited.containsKey(next)||nCost < visited.get(next).getCost()){ //Проверка посещенных вершин и их стоимости
                    visited.put(next,new PointAndCost(current.getPoint(), nCost));
                    front.offer(new PointAndCost(next, nCost));
                }
            }
        }

        throw new ADijkException("No path from start to end. Start = " + start + ", End = " + end); //если алгоритм по тем или иным причинам не смог найти путь
    }

    private List<Point> neighbors(Point point){
        List<Point> neighbors = new ArrayList<>();

        //top left в данном случае практически бесполезно
        Point topNeighbors = point.getLocation();
        Point leftNeighbors = point.getLocation();

        topNeighbors.translate(0,-1);
        leftNeighbors.translate(-1,0);

        if(verificationPoint(topNeighbors))
            neighbors.add(topNeighbors);
        if(verificationPoint(leftNeighbors))
            neighbors.add(leftNeighbors);

        //bottom right
        Point bottomNeighbors = point.getLocation();
        Point rightNeighbors = point.getLocation();

        bottomNeighbors.translate(0,1);
        rightNeighbors.translate(1,0);

        if(verificationPoint(bottomNeighbors))
            neighbors.add(bottomNeighbors);
        if(verificationPoint(rightNeighbors))
            neighbors.add(rightNeighbors);

        return neighbors;
    }

    private boolean verificationPoint(Point current){
        return current.x >= 0 && current.x <= worldWidth - 1 && current.y >= 0 && current.y <= worldHeight - 1;//верифицирует точку
    }
}
