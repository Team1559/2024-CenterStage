package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.led.Leds;
import frc.robot.subsystems.shooter.ColorSensor;
import frc.robot.subsystems.shooter.Feeder;
import frc.robot.subsystems.shooter.Flywheel;
import frc.robot.subsystems.shooter.Intake;

public class ShooterCommands {

  /** Makes Class non-instantiable */
  private ShooterCommands() {
  }

  // ========================= Default Commands =========================
  public static Command defaultIntakeCommand(Intake intake, ColorSensor sensor) {
    return Commands.run(() -> {
      if (sensor.isObjectDetected()) {
        intake.stop();
      } else {
        intake.start();
      }
    }, intake);
  }

  public static Command defaultFeederCommand(Feeder feeder, ColorSensor sensor) {
    return Commands.run(() -> {
      if (sensor.isObjectDetected()) {
        feeder.stop();
      } else {
        feeder.start();
      }
    }, feeder);
  }

  // ========================= Other Commands =========================

  public static Command reverseShooterCommand(Flywheel flywheel, Feeder feeder, Intake intake, Leds leds) {
    Command reverseShooterCommand = new Command() {
      @Override
      public void execute() {
        flywheel.reverseFlywheel();
        feeder.reverse();
        intake.reverse();
        leds.setDynamicPattern(new Color[] { Color.kRed, Color.kRed, Color.kBlack, Color.kBlack }, true);
      }

      @Override
      public void end(boolean interrupted) {
        flywheel.stop();
        feeder.stop();
        intake.stop();
        leds.setAllianceColor();
      }
    };
    reverseShooterCommand.addRequirements(flywheel, feeder, intake, leds);
    return reverseShooterCommand;
  }

  public static Command stopIntakeFeederCommand(Intake intake, Feeder feeder, Leds leds) {
    return new InstantCommand(() -> {
      intake.stop();
      feeder.stop();
      leds.setDynamicPattern(new Color[] { Color.kRed, Color.kRed, Color.kBlack, Color.kBlack }, true);
    }, intake, feeder);
  }

  public static Command shootCommand(Flywheel flywheel, Feeder feeder, Leds leds, ColorSensor colorSensor) {
    //@formatter:off
    return new SequentialCommandGroup(
      spinUpFlywheelCommand(flywheel),
      feeder.startCommand(),
      leds.setColorCommand(Color.kMediumSpringGreen),
      colorSensor.waitForNoObjectCommand(),
      new WaitCommand(.25),
      feeder.stopCommand(),
      flywheel.stopFlywheelCommand()
    );
    //@formatter:on
  }

  public static Command spinUpFlywheelCommand(Flywheel flywheel) {
    //@formatter:off
    return new SequentialCommandGroup(
      flywheel.startFlywheelCommand(), 
      new WaitCommand(0.5)
      );
      //@formattter:on
  }
  public static Command autoShootCommand(Feeder feeder, Leds leds, ColorSensor colorSensor) {
    //@formatter:off
    return new SequentialCommandGroup(
      feeder.startCommand(),
      LedCommands.blinkCommand(leds, Color.kOrange),
      colorSensor.waitForNoObjectCommand(),
      new WaitCommand(.25),
      feeder.stopCommand());
    //@formatter:on
  }
}
