package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

  private MotorController motor1;
  private int m_motor1_port = 0;

  private MotorController motor2;
  private int m_motor2_port = 1; 

  private double m_seconds = 0;
  private double m_seconds_periodic = 0; 
  private double m_seconds_remaining = m_seconds; 
  private boolean m_reverse = false;

  
  
  // Smartdashboard Values
  private boolean m_auto = false;
  private double m_RampUp = 5; 
  private double m_speed = 0.0;
  private int m_repetitions = 0;
  private int m_timeToReverse = 15;

  // Constants
  private double kFrequency = 0.002;


  public Robot() {
    motor1 = new WPI_TalonFX(m_motor1_port);
    motor2 = new WPI_TalonFX(m_motor2_port);

  }
  
  private void spin(double speed) {
    motor1.set(speed);
    motor2.set(-speed);
  }

  private void onDataChange() {
    m_seconds = SmartDashboard.getNumber("Seconds", m_seconds);
    m_speed = SmartDashboard.getNumber("Speed", m_speed);
    if (m_speed > 1) {m_speed = m_speed/100;}
    m_RampUp = SmartDashboard.getNumber("Ramp Up", m_RampUp);
    /// TODO: get values from SENDABLE CHOOSER
  }

  private double getSpeed() {
    double speed = m_speed;
    // RAMP UP
    if (m_seconds < m_RampUp) {
      return (speed/m_RampUp)/m_seconds;
    }

    // REVERSING GEAR BOX
    if (m_seconds_periodic == m_timeToReverse) {
      if (m_reverse) {
        m_reverse = false;
      } else {
        m_reverse = true; 
      }
      m_seconds_periodic = 0; 
    }

    if (m_reverse) {
    return -speed;
    } else {
      return speed;
    }
  }

  @Override
  public void teleopInit() {
    m_speed = 0.0;
    spin(m_speed);
  }

  @Override
  public void teleopPeriodic() {
    onDataChange();
    if (m_auto) {
      m_repetitions+=1;
      m_seconds_periodic += kFrequency; 
      m_seconds_remaining -= kFrequency; 
      SmartDashboard.updateValues();
      spin(getSpeed());
    } else {
      spin(m_speed);
    }
  }
  
  @Override
  public void robotInit() {
    SendableChooser<Boolean> auto = new SendableChooser<Boolean>();
    auto.addOption("Auto", true);
    auto.addOption("Manual", false);
    auto.setDefaultOption("Auto", true);
    SmartDashboard.putData(auto);
    SmartDashboard.putBoolean("Auto", true); // CHANGEABLE
    SmartDashboard.putNumber("Seconds Remaining", m_seconds_remaining); // STATIC
    SmartDashboard.putNumber("Seconds", m_seconds); // CHANGEABLE
    SmartDashboard.putNumber("Speed", m_speed); // CHANGEABLE
    SmartDashboard.putNumber("Ramp Up", m_RampUp); // CHANGEABLE
    SmartDashboard.putNumber("Repetitions", m_repetitions); // STATIC
    SmartDashboard.putBoolean("Reverse?", m_reverse); // STATIC
  }

  @Override
  public void robotPeriodic() {
    m_speed = Math.min(1.0, Math.max(-1.0, m_speed));
  }



}
