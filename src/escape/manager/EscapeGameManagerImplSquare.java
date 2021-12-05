package escape.manager;

import escape.component.MyCoordinate;
import escape.component.MyLocation;
import escape.gamedef.EscapePiece;
import escape.gamedef.LocationType;
import escape.util.EscapeGameInitializer;
import escape.util.PieceAttribute;

import java.util.*;

public class EscapeGameManagerImplSquare extends EscapeGameManagerImpl {
    public EscapeGameManagerImplSquare(EscapeGameInitializer config) {
        super(config);
        setupBoard();
    }

    @Override
    public List<MyLocation> findPath(MyLocation from, MyLocation to) {
        Queue<List<MyLocation>> queue = new LinkedList<>();
        List<MyLocation> exitPath = null;
        queue.add(new ArrayList<>(Collections.singletonList(from)));
        while(!queue.isEmpty()) {
            List<MyLocation> path = queue.remove();
            MyLocation currentLocation = path.get(path.size() - 1);
            if(currentLocation.equals(to)) {
                if(path.stream().anyMatch(l -> l.getLocationType() == LocationType.EXIT)) {
                    exitPath = path;
                } else {
                    return path;
                }
            }
            PieceAttribute pieceDistance = from.getPiece().getDescriptor().getAttribute(EscapePiece.PieceAttributeID.DISTANCE);
            if(path.size() >= pieceDistance.getValue()) {
                continue;
            }
            List<MyLocation> neighbors = this.validNeighbors(currentLocation);
            for(MyLocation l : neighbors) {
                List<MyLocation> newPath = new ArrayList<>(path);
                newPath.add(l);
                queue.add(newPath);
            }
        }
        return exitPath;
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
                if(newLocation != null && (newLocation.canMoveOver(source.getPiece()) || newLocation.getLocationType() == LocationType.EXIT)) {
                    neighbors.add(newLocation);
                }

            }
        }
        return neighbors;
    }

}
