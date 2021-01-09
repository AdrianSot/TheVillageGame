package entities;

import java.awt.Point;

public abstract class Habitant extends Entity {

  HabitantType habitantType;

  public void move(Point position) {
  }

  public HabitantType getHabitantType() {
  return habitantType;
  }

}