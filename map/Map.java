package map;

import entities.Entity;
import java.awt.Point;
import java.util.Vector;

public class Map {

  private int sizeInCells;

  private int sizePx;

  private int cellSizePx;

  private Point startPoint;

  private Point endPoint;

    /**
   * 
   * @element-type Cell
   */
  public Vector  myCell;
    /**
   * 
   * @element-type Entity
   */
  public Vector  myEntity;
   

  public Entity getEntityInPosition(Point position) {
  return null;
  }

  public void addEntity(Entity entity) {
  }

  public Point getStartPoint() {
  return null;
  }

  public Point getEndPoint() {
  return null;
  }

  public Entity getEntityWithId() {
  return null;
  }

}
