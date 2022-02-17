package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Robot extends TimedRobot {
    // CONFIGURATION VARIABLES
    //  --- Init Motor Controllers
    private String m_MotorControllerType = "FX"; // SRX | FX | SPX
    private int m_NumberOfControllers = 3;
    private ArrayList<Integer> m_canIDs = new ArrayList<Integer>();
    private ArrayList<MotorController> m_MotorControllerList = new ArrayList<MotorController>();

    //  --- Speed Variables
    private double m_speed = 0.3;
    private double m_rampUp = 2; 
    private double m_rampDown = 1; 

    //  --- Cycle Variables
    private double m_cycleTime = 15; // AKA Time to reverse
    private boolean m_reverse = false;
    private int m_cycles = 0;

    //  --- Logic Variables
    private boolean m_autoRun = false;
    private boolean m_enabled = false;

    //  --- Targets
    private String m_runTarget = "TIME"; // TIME or REP
    private double m_runExpiry;
    private int m_repExpiry;

    //  --- Constants
    private double kFrequency = 0.02;



    private void addMotorControllerToList(int canID) {
        MotorController _motorController; 
        if (m_MotorControllerType == "SRX") {
            _motorController = new WPI_TalonSRX(canID);
          } else if (m_MotorControllerType == "FX") {
            _motorController = new WPI_TalonFX(canID);
          } else if (m_MotorControllerType == "SPX") {
            _motorController = new WPI_VictorSPX(canID);
          }
          else {
              System.out.println("No Valid Motor Controller Type");
              System.exit(1);
          }
        m_MotorControllerList.add(_motorController);
        
          
    }
    private void initMotorControllers() {
        if (m_canIDs.size() == 0) {
            System.out.println("Error: CAN ID List is Empty");
        }
        for (int i = 0; i <= m_canIDs.size(); i++) { // TODO: CHECK LOGIC HERE
            System.out.println("Initialized Motor Controller. (TYPE: '"+m_MotorControllerType+"' | CAN ID: "+m_canIDs.get(i));
            addMotorControllerToList(m_canIDs.get(i));
        }
    }

    private void stupidDashboard() {
        if (!m_enabled) {
            Shuffleboard.selectTab("Configuration");

            // ADDING VALUES
            Shuffleboard.getTab("Configuration")
                .add("Speed", m_speed);
            Shuffleboard.getTab("Configuration")
                .add("Ramp Up Time: ", m_rampUp);
            Shuffleboard.getTab("Configuration")
                .add("Ramp Down Time: ", m_rampDown);
            Shuffleboard.getTab("Configuration")
                .add
        }
        else if (m_autoRun) {
            Shuffleboard.selectTab("Auto Mode");

            Shuffleboard.getTab("Auto Mode")
                .add("Speed", m_speed);
            Shuffleboard.getTab("Auto Mode")
                .add("Ramp Up: ", m_rampUp);
            Shuffleboard.getTab("Auto Mode")
                .add("Ramp Down: ", m_rampDown);
            Shuffleboard.getTab("Auto Mode")
                .add("Cycles:", m_cycles);
        }
        else {
            Shuffleboard.selectTab("Manual Operating Mode");
            Shuffleboard.getTab("Manual Operating Mode")
                .add("Speed", m_speed);
    }
        

    }



    public Robot() {

    }
}
