package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;


/**
 * Created by nihalmahajani on 1/8/18.
 */

public class TeleOperation extends LinearOpMode {
    HardwareRobot robot = new HardwareRobot();

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        while (opModeIsActive()) {

            double leftPower;
            double rightPower;

            double gPadLeftY = gamepad1.left_stick_y;
            double gPadRightY = gamepad1.right_stick_y;

            leftPower = Range.clip(gPadLeftY, -1.0, 1.0);
            rightPower = Range.clip(gPadRightY, -1.0, 1.0);

            if (gPadLeftY == 0 && gPadRightY == 0) {

                while ( leftPower >= 0.5 && rightPower >= 0.5 ) {

                    leftPower -=  0.1;
                    rightPower -= 0.1;

                    robot.setAllLeftDrivePower(leftPower);
                    robot.setAllRightDrivePower(rightPower);

                }

                leftPower = 0;
                rightPower = 0;

            }

            robot.setAllLeftDrivePower(leftPower);
            robot.setAllRightDrivePower(rightPower);

        }
    }
}

