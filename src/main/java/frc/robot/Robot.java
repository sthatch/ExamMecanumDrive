// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/** This is a demo program showing how to use Mecanum control with the MecanumDrive class. */
public class Robot extends TimedRobot {
  private static final int kFrontLeftFrontChannel = 4;
  private static final int kRearLeftFrontChannel = 6;
  private static final int kFrontRightFrontChannel = 0;
  private static final int kRearRightFrontChannel = 2;

  private static final int kFrontLeftRearChannel = 5;
  private static final int kRearLeftRearChannel = 7;
  private static final int kFrontRightRearChannel = 1;
  private static final int kRearRightRearChannel = 3;

  private static final int kJoystickChannel = 0;

  private MecanumDrive m_robotDrive;
  private XboxController m_stick;

  @Override
  public void robotInit() {
    WPI_TalonSRX frontLeftFront = new WPI_TalonSRX(kFrontLeftFrontChannel);
    WPI_TalonSRX frontLeftRear = new WPI_TalonSRX(kFrontLeftRearChannel);
    MotorControllerGroup frontLeft = new MotorControllerGroup(frontLeftFront,frontLeftRear);

    WPI_TalonSRX rearLeftFront = new WPI_TalonSRX(kRearLeftFrontChannel);
    WPI_TalonSRX rearLeftRear = new WPI_TalonSRX(kRearLeftRearChannel);
    MotorControllerGroup rearLeft = new MotorControllerGroup(rearLeftFront, rearLeftRear);

    WPI_TalonSRX frontRightFront = new WPI_TalonSRX(kFrontRightFrontChannel);
    WPI_TalonSRX frontRightRear = new WPI_TalonSRX(kFrontRightRearChannel);
    MotorControllerGroup frontRight = new MotorControllerGroup(frontRightFront, frontRightRear);

    WPI_TalonSRX rearRightFront = new WPI_TalonSRX(kRearRightFrontChannel);
    WPI_TalonSRX rearRightRear = new WPI_TalonSRX(kRearRightRearChannel);
    MotorControllerGroup rearRight = new MotorControllerGroup(rearRightFront, rearRightRear);

    // Invert the right side motors.
    // You may need to change or remove this to match your robot.
    frontRight.setInverted(true);
    rearRight.setInverted(true);

    m_robotDrive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);

    m_stick = new XboxController(kJoystickChannel);
  }

  @Override
  public void teleopPeriodic() {
    // Use the joystick X axis for forward movement, Y axis for lateral
    // movement, and Z axis for rotation.
    m_robotDrive.driveCartesian(-m_stick.getLeftY(), -m_stick.getLeftX(), -m_stick.getRightX());
  }
}
