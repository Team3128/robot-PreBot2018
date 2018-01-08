/*
 * Created By: Adham Elarabawy
 * Date: 1/8/2018
 * Description: Drives the PreBot for a distance of 100 inches so that the PIDS 
 * can easily be tuned(by checking distance and acceleration/deceleration).
 *
 */
package org.team3128.autonomous;

import org.team3128.common.util.units.Length;
import org.team3128.main.MainPreBot;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CalibrateRunPID extends CommandGroup {
	public CalibrateRunPID(MainPreBot robot) 
	{
		//not sure of the length of run(second parameter)
		addSequential(robot.drive.new CmdMoveForward(100 * Length.in, 5000, 1));
	}
}
