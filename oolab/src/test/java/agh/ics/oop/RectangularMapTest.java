package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {

    @Test
    void testMap() {
        RectangularMap map1 = new RectangularMap(4, 4);
        assertEquals(new Vector2d(0, 0), map1.getLeftEdge());
        assertEquals(new Vector2d(4, 4), map1.getRightEgde());

        RectangularMap map2 = new RectangularMap(-6, 3);
        assertEquals(new Vector2d(0, 0), map2.getLeftEdge());
        assertEquals(new Vector2d(6, 3), map2.getRightEgde());

        RectangularMap map3 = new RectangularMap(0, 0);
        assertNotEquals(new Vector2d(0, 0), map3.getLeftEdge());
        assertNotEquals(new Vector2d(4, 4), map3.getRightEgde());

    }
    @Test
    void testToString() {
        AbstractWorldMap map1 = new RectangularMap(4, 4);
        Animal animal1 = new Animal(map1);

        String output1 = " y\\x  0 1 2 3 4" + System.lineSeparator() +
                "  5: -----------" + System.lineSeparator() +
                "  4: | | | | | |" + System.lineSeparator() +
                "  3: | | | | | |" + System.lineSeparator() +
                "  2: | | |N| | |" + System.lineSeparator() +
                "  1: | | | | | |" + System.lineSeparator() +
                "  0: | | | | | |" + System.lineSeparator() +
                " -1: -----------" + System.lineSeparator();
        assertEquals(output1, map1.toString());


        AbstractWorldMap map2 = new RectangularMap(4, 4);
        Animal animal2 = new Animal(map2);
        Animal animal3 = new Animal(map2, new Vector2d(3,3));
        String output2 = " y\\x  0 1 2 3 4" + System.lineSeparator() +
                "  5: -----------" + System.lineSeparator() +
                "  4: | | | | | |" + System.lineSeparator() +
                "  3: | | | |N| |" + System.lineSeparator() +
                "  2: | | |N| | |" + System.lineSeparator() +
                "  1: | | | | | |" + System.lineSeparator() +
                "  0: | | | | | |" + System.lineSeparator() +
                " -1: -----------" + System.lineSeparator();
        assertEquals(output2, map2.toString());

        AbstractWorldMap map3 = new RectangularMap(4, 4);
        Animal animal4 = new Animal(map3);

        String output3 = " y\\x  0 1 2 3 4" + System.lineSeparator() +
                "  5: -----------" + System.lineSeparator() +
                "  4: | | | | | |" + System.lineSeparator() +
                "  3: | | |N| | |" + System.lineSeparator() +
                "  2: | | | | | |" + System.lineSeparator() +
                "  1: | | | | | |" + System.lineSeparator() +
                "  0: | | | | | |" + System.lineSeparator() +
                " -1: -----------" + System.lineSeparator();
        assertNotEquals(output3, map3.toString());


        AbstractWorldMap map4 = new RectangularMap(4, 4);
        Animal animal5 = new Animal(map4);
        Animal animal6 = new Animal(map4, new Vector2d(3,3));
        String output4 = " y\\x  0 1 2 3 4" + System.lineSeparator() +
                "  5: -----------" + System.lineSeparator() +
                "  4: | |N| | | |" + System.lineSeparator() +
                "  3: | | | | | |" + System.lineSeparator() +
                "  2: | | |N| | |" + System.lineSeparator() +
                "  1: | | | | | |" + System.lineSeparator() +
                "  0: | |N| | | |" + System.lineSeparator() +
                " -1: -----------" + System.lineSeparator();
        assertNotEquals(output4, map4.toString());


    }

    @Test
    void isOccupied() {
        AbstractWorldMap map1 = new RectangularMap(4, 4);
        assertFalse(map1.isOccupied(new Vector2d(-2, 2)));

        AbstractWorldMap map2 = new RectangularMap(4, 4);
        assertFalse(map2.isOccupied(new Vector2d(-5, -1)));

        AbstractWorldMap map3 = new RectangularMap(4, 4);
        assertFalse(map3.isOccupied(new Vector2d(5, 1)));

        AbstractWorldMap map4 = new RectangularMap(4, 4);
        Animal animal1 = new Animal(map4);
        assertTrue(map4.isOccupied(new Vector2d(2, 2)));

        AbstractWorldMap map5 = new RectangularMap(4, 4);
        Animal animal2 = new Animal(map5, new Vector2d(3,3));
        assertTrue(map5.isOccupied(new Vector2d(3, 3)));

        AbstractWorldMap map6 = new RectangularMap(4, 4);
        Animal animal3 = new Animal(map6, new Vector2d(-3,3));
        assertFalse(map6.isOccupied(new Vector2d(3, 3)));
    }

    @Test
    void canMoveTo() {
        AbstractWorldMap map1 = new RectangularMap(4, 4);
        assertTrue(map1.canMoveTo(new Vector2d(2, 2)));

        AbstractWorldMap map2 = new RectangularMap(4, 4);
        assertFalse(map2.canMoveTo(new Vector2d(4, 6)));

        AbstractWorldMap map3 = new RectangularMap(4, 4);
        assertFalse(map3.canMoveTo(new Vector2d(5, 5)));

        AbstractWorldMap map4 = new RectangularMap(4, 4);
        assertFalse(map4.canMoveTo(new Vector2d(-2, 0)));

        AbstractWorldMap map5 = new RectangularMap(4, 4);
        Animal animal1 = new Animal(map5);
        assertFalse(map5.canMoveTo(new Vector2d(2, 2)));

        AbstractWorldMap map6 = new RectangularMap(4, 4);
        Animal animal2 = new Animal(map6);
        animal2.move(MoveDirection.FORWARD);
        assertTrue(map6.canMoveTo(new Vector2d(2, 2)));

        AbstractWorldMap map7 = new RectangularMap(4, 4);
        Animal animal3 = new Animal(map7);
        animal3.move(MoveDirection.BACKWARD);
        assertTrue(map7.canMoveTo(new Vector2d(2, 2)));

        AbstractWorldMap map8 = new RectangularMap(4, 4);
        Animal animal4 = new Animal(map8);
        animal4.move(MoveDirection.LEFT);
        assertFalse(map8.canMoveTo(new Vector2d(2, 2)));
    }

    @Test
    void place() {

        AbstractWorldMap map1 = new RectangularMap(4, 4);
        assertEquals( new Animal(map1, new Vector2d(2,3)),map1.objectAt(new Vector2d(2,3)));
//        assertTrue( new Animal(map1, new Vector2d(2,3)) ==map1.objectAt(new Vector2d(2,3)));

        AbstractWorldMap map2 = new RectangularMap(4, 4);
        Animal animal1 =  new Animal(map2, new Vector2d(2,3));
        animal1.move(MoveDirection.FORWARD);
        assertEquals(animal1,map2.objectAt(new Vector2d(2,4)));


        AbstractWorldMap map3 = new RectangularMap(4, 4);
        Animal animal2 =  new Animal(map3, new Vector2d(2,3));
        animal1.move(MoveDirection.BACKWARD);
        assertEquals(animal2,map3.objectAt(new Vector2d(2,3)));


        AbstractWorldMap map4 = new RectangularMap(4, 4);
        assertNotEquals( new Animal(map4, new Vector2d(3,3)),map4.objectAt(new Vector2d(1,2)));

        AbstractWorldMap map5 = new RectangularMap(4, 4);
        assertNotEquals( new Animal(map5),map5.objectAt(new Vector2d(-1,2)));

        AbstractWorldMap map6 = new RectangularMap(4, 4);
        assertNotEquals( new Animal(map6, new Vector2d(-3,-3)),map6.objectAt(new Vector2d(3,3)));
    }

    @Test
    void objectAt() {
        AbstractWorldMap map1 = new RectangularMap(4, 4);
        assertNull(map1.objectAt(new Vector2d(2, 2)));

        assertNull(map1.objectAt(new Vector2d(4, 2)));

        assertNull(map1.objectAt(new Vector2d(-3, 2)));

        assertNull(map1.objectAt(new Vector2d(-3, -1)));

        assertNull(map1.objectAt(new Vector2d(5, 6)));


        AbstractWorldMap map2 = new RectangularMap(4, 4);
        Animal animal1 = new Animal(map2);
        assertSame(animal1, map2.objectAt(new Vector2d(2, 2)));

        AbstractWorldMap map3 = new RectangularMap(4, 4);
        Animal animal2 = new Animal(map3, new Vector2d(3,3));
        assertSame(animal2, map3.objectAt(new Vector2d(3, 3)));

        AbstractWorldMap map4 = new RectangularMap(4, 4);
        Animal animal3 = new Animal(map4, new Vector2d(1,3));
        assertNotSame(animal3, map4.objectAt(new Vector2d(1, 2)));

    }
}