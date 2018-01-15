
package org.usfirst.frc.team4610.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4610.robot.commands.ExampleCommand;
import org.usfirst.frc.team4610.robot.subsystems.ExampleSubsystem;

import com.ctre.CANTalon;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
//this is where we define all of our parts.
	
	//drive base parts
	CANTalon FrontLeft=new CANTalon(1);
	CANTalon RearLeft=new CANTalon(2);
	CANTalon FrontRight=new CANTalon(3);
	CANTalon RearRight=new CANTalon(4);
	RobotDrive chassis=new RobotDrive(FrontLeft, RearLeft, FrontRight, RearRight );
	Victor lift=new Victor(0);
	Compressor c=new Compressor(0);
	DoubleSolenoid o1=new DoubleSolenoid(1,2);
	DoubleSolenoid o2=new DoubleSolenoid(3,4);
	DoubleSolenoid s1=new DoubleSolenoid(5,6);
	DoubleSolenoid s2=new DoubleSolenoid(7,8);
	Joystick left=new Joystick(1);
	Joystick right=new Joystick(2);
	Joystick control=new Joystick(3);
	Joystick drive=new Joystick(0);
	Encoder e1=new Encoder(1,2); 
	
	
	//lift system parts
	
	
	//climb parts
	
	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		
		while(isAutonomous() && isEnabled()) {
			 chassis.tankDrive(5, 5);
			 
		}	
		
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		while(isOperatorControl()&&isEnabled()) {
			chassis.tankDrive(drive.getRawAxis(1), drive.getRawAxis(4));
				if(control.getRawButton(1)) {
					lift.set(0.5);	
					}	
				else{
					lift.set(0);
				}
				if(control.getRawButton(6)) {
					o1.set(DoubleSolenoid.Value.kForward);
					o2.set(DoubleSolenoid.Value.kForward);
				}
				if(control.getRawButton(7)) {
					o1.set(DoubleSolenoid.Value.kReverse);
				o2.set(DoubleSolenoid.Value.kReverse);
				}
				if(control.getRawButton(4)) {
					s1.set(DoubleSolenoid.Value.kForward);
					s2.set(DoubleSolenoid.Value.kForward);
				}
				if(control.getRawButton(5)) {
					s1.set(DoubleSolenoid.Value.kReverse);
					s2.set(DoubleSolenoid.Value.kReverse);
				}
				
		}
				
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
