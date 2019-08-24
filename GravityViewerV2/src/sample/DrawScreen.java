package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class DrawScreen extends Canvas {
    private GraphicsContext gtx;

    private int particleCount = 0;
    private final List<Particle> pList;

    public DrawScreen() {
        pList = new ArrayList<>();

    }


    private void init() {
        this.setWidth(1920);
        this.setHeight(100);



    }
}
