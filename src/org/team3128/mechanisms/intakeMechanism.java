package org.team3128.mechanisms;

import edu.wpi.first.wpilibj.VictorSP;

public class intakeMechanism
{
	public enum intakeState
	{
		STOP(0),
		FORWARD(1),
		BACKWARD(-1);
		
		public final double speed;
		
		private intakeState(double speed)
		{
			this.speed = speed;
		}

	}

	VictorSP intakeMotor;	
	intakeState IntakeState;
	
	public void intake(VictorSP intakeMotor) {
		
		this.intakeMotor = intakeMotor;
		IntakeState = intakeState.STOP;
		
	}
	
	public void setIntakeState(intakeState state) {
		
		IntakeState = state;
		intakeMotor.set(IntakeState.speed);
		
	}
	
	
	
}
