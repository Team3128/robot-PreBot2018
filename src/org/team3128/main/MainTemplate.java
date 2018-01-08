package org.team3128.main;

import org.team3128.common.NarwhalRobot;
import org.team3128.common.drive.SRXTankDrive;
import org.team3128.common.listener.ListenerManager;
import org.team3128.common.listener.controllers.ControllerExtreme3D;
import org.team3128.common.util.Constants;
import org.team3128.common.util.units.Length;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class MainTemplate extends NarwhalRobot 
{
	//Drive Train
	public SRXTankDrive drive;
	
	public TalonSRX leftDrive1, leftDrive2, leftDrive3;
	public TalonSRX rightDrive1, rightDrive2, rightDrive3;
	
	//Controls
	public ListenerManager lmRight;
	public ListenerManager lmLeft;
	
	public Joystick rightJoystick;
	public Joystick leftJoystick;
	
	//misc(general)
	public double wheelDiameter;
	public PowerDistributionPanel powerDistPanel;
	
	@Override
	protected void constructHardware() 
	{
		//drivetrain setup
		leftDrive1 = new TalonSRX(1);
		leftDrive2 = new TalonSRX(2);
		leftDrive3 = new TalonSRX(3);
		rightDrive1 = new TalonSRX(4);
		rightDrive2 = new TalonSRX(5);
		rightDrive3 = new TalonSRX(6);
		
		leftDrive1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.CAN_TIMEOUT);
		rightDrive1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.CAN_TIMEOUT);
		
		leftDrive2.set(ControlMode.Follower, leftDrive1.getDeviceID());
		rightDrive2.set(ControlMode.Follower, rightDrive2.getDeviceID());
		leftDrive3.set(ControlMode.Follower, leftDrive1.getDeviceID());
		rightDrive3.set(ControlMode.Follower, rightDrive2.getDeviceID());
		
		drive = new SRXTankDrive(leftDrive1, rightDrive1, wheelDiameter * Math.PI, 1, 25.25*Length.in, 30.5*Length.in, 400);
		
		
		//general electronics
		powerDistPanel = new PowerDistributionPanel();
		
		//listeners
		rightJoystick = new Joystick(0);
		leftJoystick = new Joystick(1);

		lmRight = new ListenerManager(rightJoystick, leftJoystick);
		lmLeft = new ListenerManager(new Joystick(3));
	}

	@Override
	protected void setupListeners() {
		lmRight.nameControl(ControllerExtreme3D.TWIST, "moveTurn");
		lmRight.nameControl(ControllerExtreme3D.JOYY, "moveForward");
		lmRight.nameControl(ControllerExtreme3D.THROTTLE, "moveThrottle");
		
	}

  
	protected void constructAutoPrograms(SendableChooser<CommandGroup> programChooser)
	{		

	}

	@Override
	protected void teleopInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void autonomousInit() {
		// TODO Auto-generated method stub
		
	}
}