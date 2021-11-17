package org.firstinspires.ftc.teamcode.FixIts.Bot_Candace;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Candace_Nate extends TwoMotorDrive_Nate{
    // Servo Vars
    public Servo flag = null;
    public HardwareMap hwBot = null;
    public Candace_Nate() {

    }

    public void initRobot(HardwareMap hwMap) {
        hwBot = hwMap;
        frontLeftMotor = hwBot.dcMotor.get("front_left_motor");
        frontRightMotor = hwBot.dcMotor.get("front_right_motor");

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);

        setMotorRunModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flag = hwBot.get(Servo.class, "flag");
        flag.setDirection(Servo.Direction.FORWARD);
    }
    public void raiseFlag() {
        flag.setPosition(0.9);
    }
    public void lowerFlag() {
        flag.setPosition(0.1);
    }
    public void waveFlagRight() {
        flag.setPosition(0.9);
        flag.setPosition(0.5);
    }
    public void waveFlagLeft() {
        flag.setPosition(0.9);
        flag.setPosition(0.5);
    }
}
