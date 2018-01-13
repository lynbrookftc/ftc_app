/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 * <p>
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a K9 robot.
 * <p>
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 * <p>
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Servo channel:  Servo to raise/lower arm: "arm"
 * Servo channel:  Servo to open/close claw: "claw"
 * <p>
 * Note: the configuration of the servos is such that:
 * As the arm servo approaches 0, the arm position moves up (away from the floor).
 * As the claw servo approaches 0, the claw opens up (drops the game element).
 */

public class HardwareRobot {

    /* Public OpMode members. */
    public DcMotor leftDriveFront = null;
    public DcMotor leftDriveBack = null;
    public DcMotor rightDriveFront = null;
    public DcMotor rightDriveBack = null;
    public CRServo cvn1 = null;
    public CRServo cvn2 = null;
    public DcMotor elev1 = null;
    public DcMotor elev2 = null;

    final static double COUNTS_PER_MOTOR_REV = 1750;
    final static double DRIVE_GEAR_REDUCTION = 0.5;
    final static double WHEEL_DIAMETER_INCHES = 4;
    final static double ROBOT_CIRCUMFERENCE = 75.659;
    public final static double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
//    public Servo    arm         = null;
//    public Servo    claw        = null;
//    public final static double ARM_HOME = 0.2;
//    public final static double CLAW_HOME = 0.2;
//    public final static double ARM_MIN_RANGE  = 0.20;
//    public final static double ARM_MAX_RANGE  = 0.90;
//    public final static double CLAW_MIN_RANGE  = 0.20;
//    public final static double CLAW_MAX_RANGE  = 0.7;

    /* Local OpMode members. */
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    /* Constructor */
    public HardwareRobot() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {

        // save reference to HW Map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftDriveFront = hwMap.get(DcMotor.class, "m1");
        rightDriveFront = hwMap.get(DcMotor.class, "m2");
        leftDriveBack = hwMap.get(DcMotor.class, "m3");
        rightDriveBack = hwMap.get(DcMotor.class, "m4");
        cvn1 = hwMap.get(CRServo.class, "s1");
        cvn2 = hwMap.get(CRServo.class, "s2");
        elev1 = hwMap.get(DcMotor.class, "e1");
        elev2 = hwMap.get(DcMotor.class, "e2");

        rightDriveFront.setDirection(DcMotor.Direction.REVERSE);
        rightDriveBack.setDirection(DcMotor.Direction.REVERSE);
        elev2.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        setAllLeftDrivePower(0);
        setAllRightDrivePower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftDriveFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDriveFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftDriveBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDriveBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Define and initialize ALL installed servos.
        //arm  = hwMap.get(Servo.class, "arm");
        //claw = hwMap.get(Servo.class, "claw");
        //arm.setPosition(ARM_HOME);
        //claw.setPosition(CLAW_HOME);

    }

    public void setAllLeftDrivePower(double power) {

        leftDriveFront.setPower(power);
        leftDriveBack.setPower(power);

    }

    public void setAllRightDrivePower(double power) {

        rightDriveFront.setPower(power);
        rightDriveBack.setPower(power);

    }

    public void driveWithEncoder(double distance, double leftPower, double rightPower) {

        int current1 = leftDriveFront.getCurrentPosition();
        int current2 = leftDriveBack.getCurrentPosition();
        int current3 = rightDriveFront.getCurrentPosition();
        int current4 = rightDriveBack.getCurrentPosition();

        int target1 = current1 + (int)(COUNTS_PER_INCH * distance);
        int target2 = current2 + (int)(COUNTS_PER_INCH * distance);
        int target3 = current3 + (int)(COUNTS_PER_INCH * distance);
        int target4 = current4 + (int)(COUNTS_PER_INCH * distance);

        leftDriveFront.setTargetPosition(target1);
        leftDriveBack.setTargetPosition(target2);
        rightDriveFront.setTargetPosition(target3);
        rightDriveBack.setTargetPosition(target4);

        setAllLeftDrivePower(leftPower);
        setAllRightDrivePower(rightPower);

        while (Math.abs(target1 - current1) > 1 && Math.abs(target2 - current2) > 1 &&
                Math.abs(target3 - current3) > 1 && Math.abs(target4 - current4) > 1) {

        }

        setAllLeftDrivePower(0);
        setAllRightDrivePower(0);
    }

    public void turnDegree(double angle) {

        int currentPos = leftDriveFront.getCurrentPosition();
        int targetPos = currentPos + (int)(COUNTS_PER_INCH * (ROBOT_CIRCUMFERENCE * (angle / 360)));

        leftDriveFront.setTargetPosition(-targetPos);
        leftDriveBack.setTargetPosition(-targetPos);
        rightDriveFront.setTargetPosition(targetPos);
        rightDriveBack.setTargetPosition(targetPos);

        setAllLeftDrivePower(-1);
        setAllRightDrivePower(1);

        while ((Math.abs(leftDriveFront.getCurrentPosition() - targetPos) > 1) &&
                (Math.abs(rightDriveFront.getCurrentPosition() - targetPos) > 1)) {

        }
    }
}