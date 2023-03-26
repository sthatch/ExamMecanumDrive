// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.MotorConstants;
import frc.robot.Constants.Drivetrain;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

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

  private double velocityX;
  private double velocityY;

  private ShuffleboardTab DrivetrainTab = Shuffleboard.getTab("Drivetrain");
  private GenericEntry velocityXEntry = DrivetrainTab.add("velocityX", 0).getEntry();
  private GenericEntry velocityYEntry = DrivetrainTab.add("velocityY", 0).getEntry();
  private GenericEntry velocityZEntry = DrivetrainTab.add("velocityZ", 0).getEntry();

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
    leftFront.setInverted(true);
    leftRear.setInverted(true);

    m_robotDrive = new MecanumDrive(leftFront, leftRear, rightFront, rightRear);

    m_stick = new XboxController(OperatorConstants.kDriverControllerPort);

    velocityX = 0.0;
    velocityY = 0.0;

  }

  @Override
  public void teleopPeriodic() {    
    velocityX = calculateVelocity(velocityX, m_stick.getLeftY());
    velocityXEntry.getDouble(velocityX);

    velocityY = calculateVelocity(velocityY, m_stick.getLeftX());
    velocityYEntry.getDouble(velocityY);
    
    velocityZEntry.getDouble(m_stick.getRightX());

    // Use the joystick X axis for forward movement, Y axis for lateral
    // movement, and Z axis for rotation.
    m_robotDrive.driveCartesian(-velocityX, -velocityY, m_stick.getRightX());
  }

  double calculateVelocity(double velocity, double target){
    double result = target;

    if(velocity != target){
      if(velocity < target){
        //Robot is accelerating
        result = velocity + Drivetrain.kMaxAcceleration;

        //Prevent the new acceleration from exceeding the targt acceleration
        if(result > target){
          result = target;
        }
      }else{
        //Robot is decelerating
        result = velocity - Drivetrain.kMaxAcceleration;

        //Prevent the new deceleration from exceeding the targt deceleration
        if(result < target){
          result = target;
        }
      }  
    }
    
    return result;
  }
}
