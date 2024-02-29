package frc.robot.subsystems.single_motor;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Temperature;
import edu.wpi.first.units.Velocity;

public interface SingleMotorIo {

    @AutoLog
    static class SingleMotorIoInputs {
        public double appliedOutput;
        public double outputCurrent;
        public double motorTemp;
        public int faults;
        public double velocity;
    }

    public void updateInputs(SingleMotorIoInputs inputs);

    public Measure<Temperature> getMaxSafeTemperature();

    public Measure<Temperature> getTemperature();

    public void setVelocity(Measure<Velocity<Angle>> velocityRPM);
}
