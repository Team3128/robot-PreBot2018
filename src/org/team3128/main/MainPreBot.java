/*
 * Created By: Adham Elarabawy
 * Date: 1/8/2018
 * Description: Setup teleop and autonomous(100 in drive) modes for 2018 PreBot for testing purposes
 *
 */

package org.team3128.main;

import org.team3128.autonomous.CalibrateRunPID;
import org.team3128.common.NarwhalRobot;
import org.team3128.common.drive.SRXTankDrive;
import org.team3128.common.listener.ListenerManager;
import org.team3128.common.listener.controllers.ControllerExtreme3D;
import org.team3128.common.listener.controltypes.Button;
import org.team3128.common.util.Constants;
import org.team3128.common.util.Log;
import org.team3128.common.util.datatypes.PIDConstants;
import org.team3128.common.util.units.Length;
import org.team3128.mechanisms.intakeMechanism;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class MainPreBot extends NarwhalRobot 
{	
	
	//Drive Train
	public double wheelDiameter;
	public SRXTankDrive drive;
	public TalonSRX leftDrive1, leftDrive2, leftDrive3;
	public TalonSRX rightDrive1, rightDrive2, rightDrive3;
	private boolean fullSpeed = false;
	
	//Controls
	public ListenerManager listenerRight;
	public ListenerManager listenerLeft;
	
	public Joystick rightJoystick;
	public Joystick leftJoystick;
	
	//Mechanisms
	public intakeMechanism intake;
	
	//Misc(general)
	public PowerDistributionPanel powerDistPanel;
	
	@Override
	protected void constructHardware() 
	{
		//Drive Train Setup
		leftDrive1 = new TalonSRX(1);
		leftDrive2 = new TalonSRX(2);
		leftDrive3 = new TalonSRX(3);
		rightDrive1 = new TalonSRX(4);
		rightDrive2 = new TalonSRX(5);
		rightDrive3 = new TalonSRX(6);
		
		//set Leaders
		leftDrive1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.CAN_TIMEOUT);
		rightDrive1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.CAN_TIMEOUT);
		
		//set Followers
		leftDrive2.set(ControlMode.Follower, leftDrive1.getDeviceID());
		rightDrive2.set(ControlMode.Follower, rightDrive1.getDeviceID());
		leftDrive3.set(ControlMode.Follower, leftDrive1.getDeviceID());
		rightDrive3.set(ControlMode.Follower, rightDrive1.getDeviceID());
		
		//create SRXTankDrive
		drive = new SRXTankDrive(leftDrive1, rightDrive1, wheelDiameter * Math.PI, 1, 25.25*Length.in, 30.5*Length.in, 400);
		
		//instantiate PDP
		powerDistPanel = new PowerDistributionPanel();
		
		//set Listeners
		rightJoystick = new Joystick(0);
		leftJoystick = new Joystick(1);

		listenerRight = new ListenerManager(rightJoystick, leftJoystick);
		listenerLeft = new ListenerManager(new Joystick(3));
	}

	@Override
	protected void setupListeners() {
		listenerRight.nameControl(ControllerExtreme3D.TWIST, "moveTurn");
		listenerRight.nameControl(ControllerExtreme3D.JOYY, "moveForward");
		listenerRight.nameControl(ControllerExtreme3D.THROTTLE, "Throttle");
		listenerRight.nameControl(new Button(1), "fullSpeed");

		
		listenerRight.addMultiListener(() -> {
			drive.arcadeDrive(listenerRight.getAxis("moveTurn"),
					listenerRight.getAxis("moveForward"),
					-1 * listenerRight.getAxis("Throttle"),
					true);
		}, "moveTurn", "MoveForward", "Throttle", "fullSpeed");
		listenerRight.addButtonDownListener("fullSpeed", this::switchFullSpeed);
		addListenerManager(listenerLeft);
		
	}

  
	protected void constructAutoPrograms(SendableChooser<CommandGroup> programChooser)
	{		
		programChooser.addDefault("None", null);
		programChooser.addObject("Calibrate PID", new CalibrateRunPID(this));
	}

	@Override
	protected void teleopInit() {
		//set full speed to true
		fullSpeed = true;
		
	}

	@Override
	protected void autonomousInit() {
		//set full speed to false
		fullSpeed = false;
		
	}
	
	public void switchFullSpeed()
	{
		fullSpeed = !fullSpeed;
	}
}