package com.example.myapplication6;

public class Brick {
    Coordinate[] coordinates;
    int state;
    BrickType type;

    Brick(BrickType type) {
        this.type = type;
        this.state = 1;
        switch (type) {
            case SQUARE:
                this.coordinates = new Coordinate[]{
                        new Coordinate(0, 5),
                        new Coordinate(1, 5),
                        new Coordinate(1, 6),
                        new Coordinate(0, 6)
                };

                break;


            case L_TYPE:
                this.coordinates = new Coordinate[]{
                        new Coordinate(0, 6),
                        new Coordinate(0, 5),
                        new Coordinate(1, 6),
                        new Coordinate(2, 6)
                };

                break;
            case T_TYPE:
                this.coordinates = new Coordinate[]{
                        new Coordinate(1, 5),
                        new Coordinate(0, 5),
                        new Coordinate(1, 6),
                        new Coordinate(2, 6)
                };

                break;
            case Z_TYPE:
                this.coordinates = new Coordinate[]{
                        new Coordinate(1, 6),
                        new Coordinate(1, 5),
                        new Coordinate(0, 5),
                        new Coordinate(2, 6)
                };

                break;

            case LINE:
                this.coordinates = new Coordinate[]{

                        new Coordinate(1, 5),
                        new Coordinate(0, 5),

                        new Coordinate(2, 5),
                        new Coordinate(3, 5)
                };

                break;
        }

    }

    public void Down() {
        for (Coordinate coord : this.coordinates) {
            coord.y = coord.y + 1;
        }
    }

    public void MoveLeft() {
        for (Coordinate coord : this.coordinates) {
            coord.x = coord.x - 1;
        }
    }

    public void MoveRight() {
        for (Coordinate coord : this.coordinates) {
            coord.x = coord.x + 1;
        }
    }

    public void Rotate() {
        if (this.type == BrickType.SQUARE) {
            return;
        }
        Coordinate center = this.coordinates[0];

        for (int i = 0; i < this.coordinates.length; i++) {
            Coordinate before = Coordinate.sub(this.coordinates[i], center);
            this.coordinates[i] = Coordinate.add(Coordinate.rotateAntiClock(before), center);
        }


    }
}
