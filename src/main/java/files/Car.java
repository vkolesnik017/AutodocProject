package files;

public class Car {

    private String brand;
    private String model;
    private String motor;

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getMotor() {
        return motor;
    }

    public String toString() {
        return "Car{" +
                "marke='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", motor=" + motor +
                '}' + "\n";
    }
}
