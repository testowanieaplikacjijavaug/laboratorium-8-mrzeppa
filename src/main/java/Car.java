public interface Car{
    boolean needsFuel();
    double getEngineTemperature();
    void driveTo(String destination);
    boolean canStart(double temperature);
    String getColorAsHex();
}