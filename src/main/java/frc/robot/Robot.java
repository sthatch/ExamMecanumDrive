// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

import com.ctre.phoenix.motorcontrol.InvertType;
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

  WPI_TalonSRX leftFront;
  WPI_TalonSRX leftFrontFollower;
  WPI_TalonSRX leftRear;
  WPI_TalonSRX leftRearFollower;
  WPI_TalonSRX rightFront;
  WPI_TalonSRX rightFrontFollower;
  WPI_TalonSRX rightRear;
  WPI_TalonSRX rightRearFollower;

  @Override
  public void robotInit() {
    leftFront = new WPI_TalonSRX(kFrontLeftFrontChannel);
    leftFrontFollower = new WPI_TalonSRX(kFrontLeftRearChannel);
    leftFrontFollower.follow(leftFront);
    leftFrontFollower.setInverted(InvertType.FollowMaster);
    
    leftRear = new WPI_TalonSRX(kRearLeftFrontChannel);
    leftRearFollower = new WPI_TalonSRX(kRearLeftRearChannel);
    leftRearFollower.follow(leftRear);
    leftRearFollower.setInverted(InvertType.FollowMaster);

    rightFront = new WPI_TalonSRX(kFrontRightFrontChannel);
    rightFrontFollower = new WPI_TalonSRX(kFrontRightRearChannel);
    rightFrontFollower.follow(rightFront);
    rightFrontFollower.setInverted(InvertType.FollowMaster);

    rightRear = new WPI_TalonSRX(kRearRightFrontChannel);
    rightRearFollower = new WPI_TalonSRX(kRearRightRearChannel);    
    rightRearFollower.follow(rightRear);
    rightRearFollower.setInverted(InvertType.FollowMaster);

    // Invert the right side motors.
    // You may need to change or remove this to match your robot.
    rightFront.setInverted(true);
    rightRear.setInverted(true);

    m_robotDrive = new MecanumDrive(leftFront, leftRear, rightFront, rightRear);

    m_stick = new XboxController(kJoystickChannel);
  }

  @Override
  public void teleopPeriodic() {
    // Use the joystick X axis for forward movement, Y axis for lateral
    // movement, and Z axis for rotation.
    m_robotDrive.driveCartesian(-m_stick.getLeftY(), -m_stick.getLeftX(), -m_stick.getRightX());
  }
}
