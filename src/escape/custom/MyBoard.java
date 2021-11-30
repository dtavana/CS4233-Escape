package escape.custom;

import java.util.HashMap;
import java.util.Map;

public class MyBoard {
    private int xMax, yMax;
    private HashMap<MyCoordinate, MyLocation> map;

    public MyBoard(int xMax, int yMax) {
        this.xMax = xMax;
        this.yMax = yMax;
        map = new HashMap<>();
    }

    public int getXMax() {
        return xMax;
    }

    public void setXMax(int xMax) {
        this.xMax = xMax;
    }

    public int getYMax() {
        return yMax;
    }

    public void setYMax(int yMax) {
        this.yMax = yMax;
    }

    public HashMap<MyCoordinate, MyLocation> getMap() {
        return map;
    }

    public void setMap(HashMap<MyCoordinate, MyLocation> map) {
        this.map = map;
    }

    public void addPair(MyCoordinate c, MyLocation l) {
        this.map.put(c, l);
    }

    public MyLocation getLocation(MyCoordinate c) {
        return this.map.get(c);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<MyCoordinate, MyLocation> row : map.entrySet()) {
            builder.append(row.getKey().toString()).append(" | ").append(row.getValue().toString()).append("\n");
        }
        return builder.toString();
    }
}
