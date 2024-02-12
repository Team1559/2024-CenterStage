package frc.robot.subsystems.shooter;

import frc.robot.Constants;
import frc.robot.subsystems.general.SingleMotorIo;
import frc.robot.subsystems.general.SingleMotorSubsystem;

public class Feeder extends SingleMotorSubsystem {
    public Feeder(SingleMotorIo io) {
        super("Feeder", io, Constants.FEEDER_FORWARD_VOLTAGE, Constants.FEEDER_REVERSE_VOLTAGE);
    }
}
