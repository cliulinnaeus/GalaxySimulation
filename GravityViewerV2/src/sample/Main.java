package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import javax.jnlp.PersistenceService;
import java.lang.reflect.Array;
import java.util.*;

public class Main extends Application {

    final Group root = new Group();
    final Xform world = new Xform();
    final Xform axisGroup = new Xform();
    final Xform particleGroup = new Xform();
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final Xform cameraXform = new Xform();
    final Xform cameraXform2 = new Xform();
    final Xform cameraXform3 = new Xform();
    private static final double AXIS_LENGTH = 250.0;
    private static final int WORLD_SIZE = 1000;
    private static final int STAR_COUNT = 200;
    private static final double CAMERA_INITIAL_DISTANCE = -10000;
    private static final double CAMERA_INITIAL_X_ANGLE = 30.0;
    private static final double CAMERA_INITIAL_Y_ANGLE = 300.0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 100000.0;
    private static final double CONTROL_MULTIPLIER = 0.1;
    private static final double SHIFT_MULTIPLIER = 10.0;
    private static final double MOUSE_SPEED = 0.5;
    private static final double ROTATION_SPEED = 3.0;
    private static final double TRACK_SPEED = 0.3;
    private static final double MASS_OF_THE_SUN = 2.0; // in unit of 10^30 kg
    private static final double LUMINOSITY_OF_THE_SUN = 1.0;
    private static final double A = 3.5;
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final double DT = 0.1;
    private static final int ITER = 1000;
    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;



    private void buildCamera() {
        root.getChildren().add(cameraXform);

        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraXform3.setRotateZ(180.0);

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);

    }



    private void buildAxes() {
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final Box xAxis = new Box(AXIS_LENGTH, 1, 1);
        final Box yAxis = new Box(1, AXIS_LENGTH, 1);
        final Box zAxis = new Box(1, 1, AXIS_LENGTH);

        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);

        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        axisGroup.setVisible(true);
        world.getChildren().addAll(axisGroup);
    }

    /**
     * go through every particle in the list
     * from their property (mass, velocity, radius) to compute color, radius, pos
     * add them sequentially to particleForm
     *
     * @param particles
     */
    public void buildParticles(List<Particle> particles) {

        for (Particle p : particles) {

            double luminosity = Math.pow(p.mass / MASS_OF_THE_SUN, A) * LUMINOSITY_OF_THE_SUN;
            Color color;
            if (luminosity < 0.01) {
                color = Color.DARKRED;
            } else if (0.01 <= luminosity && luminosity < 1) {
                color = Color.YELLOW;
            } else if (1 <= luminosity && luminosity < 500) {
                color = Color.WHITE;
            } else if (500 <= luminosity && luminosity < 1000) {
                color = Color.LIGHTBLUE;
            } else {
                color = Color.BLUE;
            }

            PhongMaterial material = new PhongMaterial();
            material.setSpecularColor(color);
            material.setDiffuseColor(color.brighter());

            Xform celestrialObjForm = new Xform();

            Sphere celestrialObjSphere = new Sphere(p.radius);

            celestrialObjSphere.setMaterial(material);
            celestrialObjSphere.setTranslateX(p.rx);
            celestrialObjSphere.setTranslateY(p.ry);
            celestrialObjSphere.setTranslateZ(p.rz);

            celestrialObjForm.getChildren().add(celestrialObjSphere);
            world.getChildren().add(celestrialObjForm);
        }







    }


    public void clearParticles() {

    }


    public void handleKeyboard(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case Z:
                        cameraXform2.t.setX(0.0);
                        cameraXform2.t.setY(0.0);
                        cameraXform2.setScale(1.0);
                        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
                        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
                        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);

                        break;
                    case X:
                        axisGroup.setVisible(!axisGroup.isVisible());
                        break;
                    case V:
                        particleGroup.setVisible(!particleGroup.isVisible());
                        break;
                }
            }
        });
    }

    public void handleMouse(Scene scene) {
        scene.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override public void handle(MouseEvent me) {
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override public void handle(MouseEvent me) {
                mouseOldX = mousePosX;
                mouseOldY = mousePosY;
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseDeltaX = (mousePosX - mouseOldX);
                mouseDeltaY = (mousePosY - mouseOldY);

                double modifier = 1.0;

                if (me.isControlDown()) {
                    modifier = CONTROL_MULTIPLIER;
                }
                if (me.isShiftDown()) {
                    modifier = SHIFT_MULTIPLIER;
                }
                if (me.isSecondaryButtonDown()) {
                    cameraXform.ry.setAngle(cameraXform.ry.getAngle() - mouseDeltaX*MOUSE_SPEED*modifier*ROTATION_SPEED);
                    cameraXform.rx.setAngle(cameraXform.rx.getAngle() + mouseDeltaY*MOUSE_SPEED*modifier*ROTATION_SPEED);
                }
                else if (me.isPrimaryButtonDown()) {
                    double x = camera.getTranslateX();
                    double y = camera.getTranslateY();
                    double newX = x - mouseDeltaX*MOUSE_SPEED*modifier;
                    double newY = y - mouseDeltaY*MOUSE_SPEED*modifier;
                    camera.setTranslateX(newX);
                    camera.setTranslateY(newY);
                }
            }
        });
    }

    public void handleScroll(Scene scene) {
        scene.setOnScroll(new EventHandler<javafx.scene.input.ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                double z = camera.getTranslateZ();
                double newZ = z + event.getDeltaY();
                camera.setTranslateZ(newZ);
            }
        });
    }

    public List<Particle> makeParticleList(int count) {
        ArrayList<Particle> particleList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            double rx = random.nextInt(WORLD_SIZE) - WORLD_SIZE / 2;
            double ry = random.nextInt(WORLD_SIZE) - WORLD_SIZE / 2;
            double rz = random.nextInt(WORLD_SIZE) - WORLD_SIZE / 2;
            double vx = random.nextInt(8) - 4;
            double vy = random.nextInt(8) - 4;
            double vz = random.nextInt(8) - 4;
//            double vx = 0;
//            double vy = 0;
//            double vz = 0;
            double mass = random.nextInt(100) + 1;
            double radius = random.nextInt(30);
            Particle p = new Particle(rx, ry, rz, vx, vy, vz, mass, radius);
            particleList.add(p);
        }
        return particleList;
    }



    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setResizable(false);

        root.getChildren().add(world);
        root.setDepthTest(DepthTest.ENABLE);


        List<Particle> pList = makeParticleList(STAR_COUNT);

        buildCamera();
        buildAxes();
        buildParticles(pList);



        Solver solver = new EulerSolver();
        Engine engine = new Engine(WORLD_SIZE / 2, -WORLD_SIZE / 2, -WORLD_SIZE / 2,
                WORLD_SIZE, solver, pList, DT);

        // _______________________________________________ running simulator
        for (int i = 0; i < ITER; i++) {
            world.getChildren().removeAll(particleGroup);

            engine.stepForward();
            buildParticles(pList);
            Thread.sleep(1);
        }
        //________________________________________________

        Scene scene = new Scene(root, 1024, 600, true);
        scene.setFill(BACKGROUND_COLOR);

        handleKeyboard(scene);
        handleMouse(scene);
        handleScroll(scene);

        primaryStage.setTitle("GravityViewer 2.0");
        primaryStage.setScene(scene);

        primaryStage.show();
        scene.setCamera(camera);
    }






    public static void main(String[] args) {
        launch(args);
    }







}


