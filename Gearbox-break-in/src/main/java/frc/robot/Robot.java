package frc.robot;

import java.util.concurrent.TimeUnit;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {

  private MotorController motor1;
  private int m_motor1_port = 0;

  private MotorController motor2;
  private int m_motor2_port = 1; 

  private double m_seconds = 0;
  private double m_desiredSpeed = 0.0;

  private double m_num_repitions = 0; // If 0, repetitions is infinite
  private XboxController xbox; 

  // Smartdashboard Values
  private double m_speed = 0.4;
  private int m_repetitions = 15;
  private int m_timeToReverse = 15;



  public Robot() {
    motor1 = new WPI_TalonFX(m_motor1_port);
    motor2 = new WPI_TalonFX(m_motor2_port);
    xbox = new XboxController(1);
  }
  
  private void spin(double speed) {
    motor1.set(speed);
    motor2.set(-speed);
  }

  private void breakIn() {
    // The frequency of this being called is every 0.2 seconds
    // so increment by 0.2
    m_repetitions+=1;
    if (m_repetitions != m_num_repitions) {
      if (m_timeToReverse == m_seconds) {
        m_speed=-m_speed;
        m_seconds=0;
      }
    spin(m_speed);
    m_seconds += .002; 
  }}
  
  @Override
  public void teleopInit() {
    m_desiredSpeed = 0.0;
    spin(m_desiredSpeed);
  }

  
  @Override
  public void robotInit() {
  //  SendableChooser<Command> m_chooser = new SendableChooser<>();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    if (xbox.getAButtonPressed()){
      m_speed += 0.1;
      System.out.println(m_speed);
    }

    if (xbox.getBButtonPressed()){
      m_speed -= 0.1;
      System.out.println(m_speed);
    }

    if (xbox.getXButtonPressed()){
      breakIn();
    }
    if (xbox.getYButtonPressed()){
      m_speed = 0.0;
    }

    m_speed = Math.min(1.0, Math.max(-1.0, m_speed));
    //CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}



}
