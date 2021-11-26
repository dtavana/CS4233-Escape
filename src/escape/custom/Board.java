package escape.custom;

import java.util.HashMap;
import java.util.Map;

public class Board {
    int xMax, yMax;
    private HashMap<MyCoordinate, MyPiece> map;

    public Board(int xMax, int yMax) {
        this.xMax = xMax;
        this.yMax = yMax;
        map = new HashMap<>();
    }

    public void addPair(MyCoordinate c, MyPiece p) {
        this.map.put(c, p);
    }

    public MyPiece get(MyCoordinate c) {
        return this.map.get(c);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(Map.Entry row : map.entrySet()) {
            builder.append(row.getKey().toString()).append(" | ").append(row.getValue().toString()).append("\n");
        }
        return builder.toString();
    }
}
