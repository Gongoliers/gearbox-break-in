package frc.robot;
 
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
 
public class Robot extends TimedRobot {
 
 
  // IMPORTANT CONFIG
  private String m_MotorControllerType = "FX"; // EIHTER SRX | FX | SPX
 
  // ONLY ONE MOTOR PORT WILL BE USED
  // MAP ALL MOTORS TO BREAK IN TO THIS PORT
  private MotorController motor1;
  private int m_motor1_port = 0;
  private double m_seconds_periodic = 0; // DONT CHANGE
 
  // CHANGE THIS STUFF
  private double m_seconds = 60*10;
  private double m_seconds_remaining = m_seconds; 
  private boolean m_reverse = false;
 
 
  // Smartdashboard Values
  private boolean m_auto = true;
  private double m_RampUp = 3; 
  private double m_RampDown = 3;
  private double m_speed = 0.3;
  private double m_speed_target = 0.3;
  private int m_repetitions = 0;
  private double m_timeToReverse = 5;
 
  // Constants
  private double kFrequency = 0.02;
 
 
  public Robot() {
    if (m_MotorControllerType == "SRX") {
    motor1 = new WPI_TalonSRX(m_motor1_port);
  } else if (m_MotorControllerType == "FX") {
    motor1 = new WPI_TalonFX(m_motor1_port);
  } else if (m_MotorControllerType == "SPX") {
    motor1 = new WPI_VictorSPX(m_motor1_port);
  }
  }
 
  private void spin(double speed) {
    motor1.set(speed);
  }
 
  private void onDataChange() {
    m_seconds = SmartDashboard.getNumber("Seconds", m_seconds);
    m_speed_target = SmartDashboard.getNumber("Speed Target", m_speed);
    if (-1 > m_speed_target | m_speed_target < 1) {m_speed_target = m_speed_target/100;}
    m_RampUp = SmartDashboard.getNumber("Ramp Up", m_RampUp);
    /// TODO: get values from SENDABLE CHOOSER
    // SmartDashboard.putBoolean("Auto", true); // CHANGEABLE
    SmartDashboard.putNumber("Seconds Remaining", m_seconds_remaining); // STATIC
    SmartDashboard.putNumber("Seconds", m_seconds);
    SmartDashboard.putNumber("Speed Target", m_speed_target)
    SmartDashboard.putNumber("Ramp Up", m_RampUp); // CHANGEABLE
    SmartDashboard.putNumber("Repetitions", m_repetitions); // STATIC
    SmartDashboard.putBoolean("Reverse?", m_reverse); // STATIC
  }
 
  private double getSpeed() {
    if (m_speed_target != m_speed && m_speed_target != 0) {
      m_speed = m_speed_target;
    }
    double speed = m_speed;
    // RAMP UP
    if (m_seconds_periodic < m_RampUp) {
      speed = (speed*(m_seconds_periodic/m_RampUp));
    }
    if ((m_seconds_periodic+m_RampDown) > m_timeToReverse) {
      speed = (speed*((m_timeToReverse-m_seconds_periodic)/m_RampDown));
    }
    // REVERSING GEAR BOX
    if (m_seconds_periodic > m_timeToReverse) {
      m_repetitions+=1;
      if (m_reverse) {
        m_reverse = false;
      } else {
        m_reverse = true; 
      }
      m_seconds_periodic = 0; 
    }
    speed = Math.round(speed*1000.0)/1000.0;
    if (m_reverse) {
    return -speed;
    } else {
      return speed;}
    }
 
  private boolean isEnd() {
    if (m_seconds_remaining == 0) {
      SmartDashboard.putNumber("Repetitions", -1);
      return true;      
    } else {return false;}
  }
 
  @Override
  public void teleopInit() {
    double speed = 0.0;
    spin(speed);
 
  }
 
  @Override
  public void teleopPeriodic() {
    if (!isEnd()) {
      onDataChange();
      if (m_auto) {
        m_seconds_periodic += kFrequency; 
        m_seconds_remaining -= kFrequency; 
        SmartDashboard.updateValues();
        double speed = getSpeed();
        SmartDashboard.putNumber("Speed", speed); // CHANGEABLE
        spin(speed);
      } else {
        spin(m_speed);
      }
  }
}
 
  @Override
  public void robotInit() {
    // TODO SENDABLECHOOSER NOT WORKING
    // SendableChooser<Boolean> auto = new SendableChooser<Boolean>();
    // auto.addOption("Auto", true);
    // auto.addOption("Manual", false);
    // auto.setDefaultOption("Auto", true);
    // SmartDashboard.putData(auto);
    SmartDashboard.putBoolean("Auto", true); // CHANGEABLE
    SmartDashboard.putNumber("Seconds Remaining", m_seconds_remaining); // STATIC
    SmartDashboard.putNumber("Seconds", m_seconds); // CHANGEABLE
    SmartDashboard.putNumber("Ramp Up", m_RampUp); // CHANGEABLE
    SmartDashboard.putNumber("Repetitions", m_repetitions); // STATIC
    SmartDashboard.putBoolean("Reverse?", m_reverse); // STATIC
  }
 
  @Override
  public void robotPeriodic() {
    m_speed = Math.min(1.0, Math.max(-1.0, m_speed));
  }
}
