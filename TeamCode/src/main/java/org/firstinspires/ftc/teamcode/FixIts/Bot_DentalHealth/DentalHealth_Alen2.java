package org.firstinspires.ftc.teamcode.FixIts.Bot_DentalHealth;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class DentalHealth_Alen2 extends TwoMotorDrive_Alen2 {

    public HardwareMap hwBot = null;

    // Define variable here
    public Servo flag = null;

    public DentalHealth_Alen2 () {

    }

    public void initRobot (HardwareMap hwMap) {

        hwBot = hwMap;
        frontLeftMotor = hwBot.dcMotor.get("front_left_motor");
        frontRightMotor = hwMap.dcMotor.get("front_right_motor");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        setMotorRunModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorRunModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //initialize servo here
        flag = hwBot.get(Servo.class, "flag");
        flag.setDirection(Servo.Direction.FORWARD);
    }

// define movement methods
    public void raiseFlag() {

        flag.setPosition(0.475);

    }

    public void lowerFlag() {

        flag.setPosition(0.3);

    }

    public void initFlag() {

        flag.setPosition(.3);

    }

    public void waveFlagRight() {

        flag.setPosition(0.4);

    }

    public void waveFlagLeft() {

        flag.setPosition(0.5);

    }
}

//JDA

