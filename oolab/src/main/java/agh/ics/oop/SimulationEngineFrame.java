package agh.ics.oop;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import javax.swing.*;
import javax.swing.border.Border;

import static java.lang.Math.abs;


public class SimulationEngineFrame implements IEngine {

    Timer timer;
    public MoveDirection[] directions;
    public IWorldMap map;
    public Vector2d[] positions;
    private final LinkedList<Animal> animals = new LinkedList<>();

    private int jj = 0;
    private int x = 0;
    static JButton[][] button;
    static JPanel[][] kratka;
    public Grass[] grass;
    public Grass[] removedGrass;
    private Color[] colors;
    JFrame frame = new JFrame("");

    public SimulationEngineFrame(MoveDirection[] directions, IWorldMap mapa, Vector2d[] pos) {
        this.directions = directions;
        this.map = mapa;
        this.positions = pos;

        colors = new Color[positions.length];
        for (int c = 0; c < positions.length; c++) {

            int aa = (int) Math.floor(Math.random() * (255 - 110 + 1) + 110);
            int b = (int) Math.floor(Math.random() * (255 + 1) + 0);
            int cc = (int) Math.floor(Math.random() * (255 + 1) + 0);
            Color col = new Color(aa, b, cc);
            colors[c] = col;
        }

    }

    public IWorldMap getMap() {
        return this.map;
    }

    public JPanel buildMap(boolean refresh) {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        grass = map.getGrass();
        removedGrass = map.getRemovedGrass();

        Vector2d[] p = map.edges();
        Vector2d left = p[1];
        Vector2d right = p[0];
        Vector2d size = new Vector2d(abs(left.x)+right.x+1, abs(left.y)+right.y+1);

        int sizeOfRec = 40;
        Border emptyBorder = BorderFactory.createEmptyBorder();

        int numOfAnimals = positions.length;

        JPanel okno = new JPanel(new GridLayout(size.y + 3, size.x + 3, 0, 0));
        button = new JButton[size.y + 4][size.x + 4];

         kratka = new JPanel[size.y + 4][size.x + 4];

        okno.setMaximumSize(new Dimension(1080, 720));
        frame.setVisible(true);
        for (int i = 0; i <= size.y + 2; i++) {

            for (int j = 0; j <= size.x + 2; j++) {

                if (j == 0) {
                    button[i][j] = new JButton(String.valueOf(size.y + 1 - (i)+ left.y));
                    button[i][j].setPreferredSize(new Dimension(sizeOfRec, sizeOfRec));
                    button[i][j].setIcon(Icons.BORDER.getIcon());
                    button[i][j].setVerticalTextPosition(SwingConstants.CENTER);
                    button[i][j].setHorizontalTextPosition(SwingConstants.CENTER);
                    button[i][j].setForeground(Color.white);

                } else if (i == 0) {
                    button[i][j] = new JButton(String.valueOf(j - 2 + left.x));
                    button[i][j].setPreferredSize(new Dimension(sizeOfRec, sizeOfRec));
                    button[i][j].setIcon(Icons.BORDER.getIcon());
                    button[i][j].setVerticalTextPosition(SwingConstants.CENTER);
                    button[i][j].setHorizontalTextPosition(SwingConstants.CENTER);
                    button[i][j].setForeground(Color.white);


                } else {
                    button[i][j] = new JButton("");
                    button[i][j].setPreferredSize(new Dimension(sizeOfRec, sizeOfRec));
                    button[i][j].setIcon(Icons.COBBLE.getIcon());


                }
                if (i == 0 && j == 0) {
                    button[i][j] = new JButton("y\\x");
                    button[i][j].setPreferredSize(new Dimension(sizeOfRec, sizeOfRec));
                    button[i][j].setIcon(Icons.BORDER.getIcon());
                    button[i][j].setVerticalTextPosition(SwingConstants.CENTER);
                    button[i][j].setHorizontalTextPosition(SwingConstants.CENTER);
                    button[i][j].setForeground(Color.white);

                }
                button[i][j].setBorder(emptyBorder);
                button[i][j].setRolloverEnabled(false);

                okno.add(button[i][j]);
            }
        }
        if (refresh){
            for (Grass d : removedGrass) {
                Vector2d positiond = d.getPosition();
                button[size.y - positiond.y + 1 + left.y][positiond.x + 2- left.x].setIcon(Icons.DIRT.getIcon());
            }
            for (int i = 0; i < grass.length; i++) {
                button[size.y - grass[i].getPosition().y + 1 + left.y][grass[i].getPosition().x + 2- left.x].setIcon(Icons.GRASS.getIcon());
            }
            int iterato = 0;
            for (Animal d : animals) {
                Vector2d positiond = d.getPosition();
                button[size.y - positiond.y + 1 + left.y][positiond.x + 2- left.x].setIcon(null);
                button[size.y - positiond.y + 1 + left.y][positiond.x + 2- left.x].setBackground(colors[iterato]);
                button[size.y - positiond.y + 1 + left.y][positiond.x + 2- left.x].setText(d.toString());
                iterato++;
            }

        }
       else {
            for (int i = 0; i < numOfAnimals; i++) {
                Animal man = new Animal(map, positions[i]);

                animals.add(man);
                button[size.y - positions[i].y + 1+ left.y][positions[i].x + 2- left.x].setBackground(colors[i]);
                button[size.y - positions[i].y + 1+ left.y][positions[i].x + 2- left.x].setText(man.toString());
            }
        }
        return okno;

    }


    @Override
    public void run() {
        AtomicReference<JPanel> okno = new AtomicReference<>(buildMap(false));

        ActionListener a = e -> {

            okno.set(buildMap(true));
            okno.get().setVisible(true);
            Vector2d[] p = map.edges();
            Vector2d left = p[1];
            Vector2d right = p[0];
            Vector2d size = new Vector2d(abs(left.x)+right.x+1, abs(left.y)+right.y+1);


            frame.setContentPane(okno.get());
            frame.pack();

            MoveDirection o = directions[x];
            if (jj == positions.length) {

                jj = 0;
            }

            Animal man = animals.get(jj);
            Vector2d state = man.getPosition();
            map.moveAnimal(man, o);

            Vector2d state2 = man.getPosition();
//            okno.set(buildMap(true));
//            okno.get().setVisible(true);
            button[size.y - state.y + 1+ left.y][state.x + 2- left.x].setIcon(Icons.COBBLE.getIcon());
            button[size.y - state.y + 1+ left.y][state.x + 2- left.x].setText("");
            button[size.y - state2.y + 1+ left.y][state2.x + 2- left.x].setIcon(null);
            button[size.y - state2.y + 1+ left.y][state2.x + 2- left.x].setBackground(colors[jj]);
            button[size.y - state2.y + 1+ left.y][state2.x + 2- left.x].setText(man.toString());

            jj++;
            x++;

            if (x == directions.length - 1) {
                timer.stop();
//                System.exit(0);
            }
        };
        timer = new Timer(100, a);
        timer.start();

    }


}
