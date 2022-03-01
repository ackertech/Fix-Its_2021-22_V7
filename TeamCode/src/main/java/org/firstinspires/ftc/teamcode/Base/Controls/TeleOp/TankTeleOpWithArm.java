package org.firstinspires.ftc.teamcode.Base.Controls.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Base.Mechanisms.ASLHand;
import org.firstinspires.ftc.teamcode.Base.Mechanisms.ArmHand;
import org.firstinspires.ftc.teamcode.Base.Robot.TankBot;

//@Disabled
@TeleOp(name = "TankBot Drive Arm")

public class TankTeleOpWithArm extends OpMode {

    //TeleOp Driving Behavior Variables
    public double speedMultiply = .75;
    public enum Style {
        ARCADE1, ARCADE2, TANK
    }
    public Style driverStyle = Style.ARCADE1;
    public double leftSidePower;
    public double rightSidePower;

    // GamePad Variables
    double leftStickYVal;
    double leftStickXVal;
    double rightStickXVal;
    double rightStickYVal;

    // Hand Variables
    String handGesture;
    String wristStatus;
    String ASLWord = "";


    // Construct the Physical Bot based on the Robot Class
    public TankBot Bot = new TankBot();
    public ArmHand Handy = new ArmHand();
    public ASLHand ASL = new ASLHand();


    // TeleOp Initialize Method.  This is the Init Button on the Driver Station Phone
    @Override
    public void init() {

        Bot.initRobot(hardwareMap);
        Handy.initArmHand(hardwareMap);

    }

    // TeleOp Loop Method.  This start AFTER clicking the Play Button on the Driver Station Phone

    public void loop() {

        speedControl();
        driveControl();
        drive();
        handControl();
        wristControl();
        elbowControl();
        lazySusanControl();
        signASL();
        telemetryOutput();

    }

    public void telemetryOutput() {
        telemetry.addData("Drive Mode: ", driverStyle);
        telemetry.addData("Speed: ", speedMultiply);
        telemetry.addData("Front Left Motor Power: ", Bot.frontLeftMotor.getPower());
        telemetry.addData("Rear Left Motor Power: ", Bot.rearLeftMotor.getPower());
        telemetry.addData("Front Right Motor Power: ", Bot.frontRightMotor.getPower());
        telemetry.addData("Rear Right Motor Power: ", Bot.rearRightMotor.getPower());
        telemetry.addData("Elbow Position: ", Handy.elbowCurrPos );
        telemetry.addData("LazySusan Position: ", Bot.lazySusanCurrPos );
        telemetry.addData("Hand Gesture: ", handGesture);
        telemetry.addData("Wrist Status: ", wristStatus);
        telemetry.addData("ASL Sentence: ", ASLWord);
        telemetry.update();

    }


    /**  ********  DRIVING METHODS USING GAMEPAD 1 *************      **/

    public void drive() {

        switch (driverStyle) {

            case ARCADE1:

                leftStickYVal = gamepad1.left_stick_y;
                leftStickYVal = Range.clip(leftStickYVal, -1, 1);
                leftStickXVal = gamepad1.left_stick_x;
                leftStickXVal = Range.clip(leftStickXVal, -1, 1);

                if (leftStickYVal < -0.1) {
                    Bot.tankDriveForward(speedMultiply*leftStickYVal);
                } else if (leftStickYVal > 0.1) {
                    Bot.tankDriveBackward(speedMultiply*leftStickYVal);
                } else if (leftStickXVal > 0.1) {
                    Bot.tankTurnRight(speedMultiply*leftStickXVal);
                } else if (leftStickXVal < -0.1) {
                    Bot.tankTurnLeft(speedMultiply*leftStickXVal);
                } else {
                    Bot.stopMotors();
                }
                break;

            case ARCADE2:

                leftStickYVal = gamepad1.left_stick_y;
                leftStickYVal = Range.clip(leftStickYVal, -1, 1);
                leftStickXVal = gamepad1.left_stick_x;
                leftStickXVal = Range.clip(leftStickXVal, -1, 1);
                rightStickYVal = gamepad1.right_stick_y;
                rightStickYVal = Range.clip(rightStickYVal, -1, 1);
                rightStickXVal = gamepad1.right_stick_x;
                rightStickXVal = Range.clip(rightStickXVal, -1, 1);

                if (leftStickYVal < -0.1) {
                    Bot.tankDriveForward(speedMultiply*leftStickYVal);
                } else if (leftStickYVal > 0.1) {
                    Bot.tankDriveBackward(speedMultiply*leftStickYVal);
                } else {
                    Bot.stopMotors();
                }
                if (rightStickXVal > 0.1) {
                    Bot.tankTurnRight(speedMultiply*rightStickXVal);
                } else if (rightStickXVal < -0.1) {
                    Bot.tankTurnLeft(speedMultiply*rightStickXVal);
                } else {
                    Bot.stopMotors();
                }
                break;

            case TANK:

                leftStickYVal = gamepad1.left_stick_y;
                leftStickYVal = Range.clip(leftStickYVal, -1, 1);
                rightStickYVal = gamepad1.right_stick_y;
                rightStickYVal = Range.clip(rightStickYVal, -1, 1);

                leftSidePower = speedMultiply * leftStickYVal * (-1);
                rightSidePower = speedMultiply * rightStickYVal * (-1);
                Bot.tankDrive(leftSidePower,rightSidePower);
                break;
        }
    }


    public void driveControl () {
            if (gamepad1.a) { driverStyle = Style.ARCADE1; }
            if (gamepad1.b) { driverStyle = Style.ARCADE2; }
            if (gamepad1.x) { driverStyle = Style.TANK; }
    }


     public void speedControl () {
            if (gamepad1.dpad_right) {
                speedMultiply = 0.25;
            } else if (gamepad1.dpad_down) {
                speedMultiply = 0.50;
            } else if (gamepad1.dpad_left) {
                speedMultiply = 0.75;
            } else if (gamepad1.dpad_up) {
                speedMultiply = 1.00;
            }
    }

    /**  ********  ARM METHODS USING GAMEPAD2 *************      **/

    public void elbowControl() {

        if (gamepad2.dpad_up  && Handy.elbowCurrPos < Handy.elbowMaxPos)
        {
            Handy.elbowCurrPos += Handy.elbowIncrements;
            Handy.elbow.setPosition(Handy.elbowCurrPos);

        }
        else {
            Handy.elbow.setPosition(Handy.elbowCurrPos);
        }

        if (gamepad2.dpad_down  && Handy.elbowCurrPos > Handy.elbowMinPOs)
        {
            Handy.elbowCurrPos -= Handy.elbowIncrements;
            Handy.elbow.setPosition(Handy.elbowCurrPos);

        }
        else {
            Handy.elbow.setPosition(Handy.elbowCurrPos);
        }
    }

    public void lazySusanControl() {

        if (gamepad2.dpad_left  && Bot.lazySusanCurrPos < Bot.lazySusanMaxPos)
        {
            Bot.lazySusanCurrPos += Bot.lazySusanIncrements;
            Bot.lazySusan.setPosition(Bot.lazySusanCurrPos);

        }
        else {
            Bot.lazySusan.setPosition(Bot.lazySusanCurrPos);
        }

        if (gamepad2.dpad_right  && Bot.lazySusanCurrPos > Bot.lazySusanMinPos)
        {
            Bot.lazySusanCurrPos -= Bot.lazySusanIncrements;
            Bot.lazySusan.setPosition(Bot.lazySusanCurrPos);

        }
        else {
            Bot.lazySusan.setPosition(Bot.lazySusanCurrPos);
        }
    }


    /**  ********  HAND METHODS USING GAMEPAD2 *************      **/

    public void handControl() {

        if (gamepad2.a) {
            Handy.point();
            handGesture = "Pointing";
        } else if (gamepad2.b) {
            Handy.surferWave();
            handGesture = "Surfer Wave";
        } else if (gamepad2.y) {
            Handy.peace();
            handGesture = "Peace Sign";
        } else if (gamepad2.x) {
            Handy.thumbsUp();
            handGesture = "Thumbs Up";
        } else if (gamepad2.left_bumper) {
            Handy.openHand();
            handGesture = "Open Hand";
        } else if (gamepad2.right_bumper) {
            Handy.closeHand();
            handGesture = "Close Hand";
        } else {
            Handy.closeHand();
            handGesture = "Close Hand";
        }
    }

    public void wristControl() {
        if (gamepad2.left_trigger > 0.1) {
            Handy.openWrist();
            wristStatus = "Open Wrist";
        }
        else if (gamepad2.right_trigger > 0.1) {
            Handy.halfWrist();
            wristStatus = "Half Wrist";
        }
        else {
            Handy.closeWrist();
            wristStatus = "Close Wrist";

        }

    }

    /**  ********  ASL METHODS USING GAMEPAD 2 *************      **/

    public void signASL() {
        if (gamepad2.dpad_up) {
            ASLWord = "I Love MBCA";
            ASL.signSentence(ASLWord);
        }
        if (gamepad2.dpad_down) {
            ASLWord = "I Love Robots";
            ASL.signSentence(ASLWord);
        }

    }

}