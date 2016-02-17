package org.usfirst.frc.team2834.robot.commands;

import org.usfirst.frc.team2834.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CenterOnGoal extends Command {
	
	private double start;
	
    public CenterOnGoal() {
    	super("Center on Goal", 5);
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//double angle = Robot.vision.getGamma() * (180.0 / Math.PI);
    	//The camera is upside-down so the angle should be negative
    	//Robot.vision.calculate();
    	Robot.drivetrain.reset();
    	Robot.drivetrain.setSetpoint(Robot.drivetrain.getYaw());
    	Robot.drivetrain.setSetpointRelative(-((Robot.vision.getGamma()/* + Robot.vision.getDelta()*/) * (180.0 / Math.PI)));
    	Robot.drivetrain.enable();
    	start = Robot.drivetrain.getPIDController().getError();
    	System.out.println("=========Got to the rotate on goal========");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.haloDrive(0.0, 0.0, true);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return start * Robot.drivetrain.getPIDController().getError() < 0 || Robot.drivetrain.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.disable();
    	Robot.drivetrain.setZero();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
