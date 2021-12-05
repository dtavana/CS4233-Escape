package escape.manager;

import escape.component.MyCoordinate;
import escape.component.MyLocation;
import escape.util.EscapeGameInitializer;

import java.util.*;

public class EscapeGameManagerImplSquare extends EscapeGameManagerImpl {
    public EscapeGameManagerImplSquare(EscapeGameInitializer config) {
        super(config);
        setupBoard();
    }

    @Override
    public boolean isValidMoveOnBoard(MyLocation from, MyLocation to) {
        List<MyLocation> path = this.findPath(from, to);
        if(path != null) {
            for(MyLocation l : path) {
                System.out.println(l);
            }
        }
        return path != null;
    }

    public List<MyLocation> findPath(MyLocation from, MyLocation to) {
        Queue<List<MyLocation>> queue = new LinkedList<>();
        queue.add(new ArrayList<>(Collections.singletonList(from)));
        while(!queue.isEmpty()) {
            List<MyLocation> path = queue.remove();
            MyLocation currentLocation = path.get(path.size() - 1);
            if(currentLocation.equals(to)) {
                return path;
            }
            List<MyLocation> neighbors = this.validNeighbors(currentLocation);
            for(MyLocation l : neighbors) {
                List<MyLocation> newPath = new ArrayList<>(path);
                newPath.add(l);
                queue.add(newPath);
            }
        }
        return null;
    }

    @Override
    public List<MyLocation> validNeighbors(MyLocation source) {
        ArrayList<MyLocation> neighbors = new ArrayList<>();
        for(int x = -1; x <= 1; x++) {
            for(int y = -1; y <= 1; y++) {
                if(x == 0 && y == 0) {
                    continue;
                }
                MyCoordinate newCoordinate = new MyCoordinate(source.getCoordinate().getX() + x, source.getCoordinate().getY() + y);
                MyLocation newLocation = this.board.getLocation(newCoordinate);
                if(newLocation != null && newLocation.canMoveOver(source.getPiece())) {
                    //System.out.println("Adding " + newLocation);
                    neighbors.add(newLocation);
                }

            }
        }
        return neighbors;
    }

}
