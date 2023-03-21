// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.MotorConstants;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/** This is a demo program showing how to use Mecanum control with the MecanumDrive class. */
public class Robot extends TimedRobot {
  private MecanumDrive m_robotDrive;
  private XboxController m_stick;

  private WPI_TalonSRX leftFront;
  private WPI_TalonSRX leftFrontFollower;
  private WPI_TalonSRX leftRear;
  private WPI_TalonSRX leftRearFollower;
  private WPI_TalonSRX rightFront;
  private WPI_TalonSRX rightFrontFollower;
  private WPI_TalonSRX rightRear;
  private WPI_TalonSRX rightRearFollower;

  @Override
  public void robotInit() {
    leftFront = new WPI_TalonSRX(MotorConstants.kLeftFrontChannel);
    leftFrontFollower = new WPI_TalonSRX(MotorConstants.kLeftFrontFollowerChannel);
    leftFrontFollower.follow(leftFront);
    leftFrontFollower.setInverted(InvertType.FollowMaster);
    
    leftRear = new WPI_TalonSRX(MotorConstants.kLeftRearChannel);
    leftRearFollower = new WPI_TalonSRX(MotorConstants.kLeftRearFollowerChannel);
    leftRearFollower.follow(leftRear);
    leftRearFollower.setInverted(InvertType.FollowMaster);

    rightFront = new WPI_TalonSRX(MotorConstants.kRightFrontChannel);
    rightFrontFollower = new WPI_TalonSRX(MotorConstants.kRightFrontFollowerChannel);
    rightFrontFollower.follow(rightFront);
    rightFrontFollower.setInverted(InvertType.FollowMaster);

    rightRear = new WPI_TalonSRX(MotorConstants.kRightRearChannel);
    rightRearFollower = new WPI_TalonSRX(MotorConstants.kRightRearFollowerChannel);    
    rightRearFollower.follow(rightRear);
    rightRearFollower.setInverted(InvertType.FollowMaster);

    // Invert the right side motors.
    // You may need to change or remove this to match your robot.
    rightFront.setInverted(true);
    rightRear.setInverted(true);

    m_robotDrive = new MecanumDrive(leftFront, leftRear, rightFront, rightRear);

    m_stick = new XboxController(OperatorConstants.kDriverControllerPort);
  }

  @Override
  public void teleopPeriodic() {
    // Use the joystick X axis for forward movement, Y axis for lateral
    // movement, and Z axis for rotation.
    m_robotDrive.driveCartesian(-m_stick.getLeftY(), -m_stick.getLeftX(), -m_stick.getRightX());
  }
}
