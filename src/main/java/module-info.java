module interview.butanski.viktor.solutions.sirma.employeespairfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.bootstrapfx.core;

    exports interview.butanski.viktor.solutions.sirma.employeespairfx.app;
    opens interview.butanski.viktor.solutions.sirma.employeespairfx.app to javafx.graphics;

    exports interview.butanski.viktor.solutions.sirma.employeespairfx.entity;
    opens interview.butanski.viktor.solutions.sirma.employeespairfx.entity to javafx.base;
}