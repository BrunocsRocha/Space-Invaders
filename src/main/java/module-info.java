module SpaceInvaders {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens SpaceInvaders to javafx.fxml;
    exports SpaceInvaders;
}
