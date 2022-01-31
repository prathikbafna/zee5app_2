package com.zee.zee5app;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.naming.InvalidNameException;

import com.zee.zee5app.dto.Register;
import com.zee.zee5app.dto.enums.ROLE;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidEmailFormatException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.exception.InvalidPasswordException;
import com.zee.zee5app.service.LoginService;
import com.zee.zee5app.service.UserService;
import com.zee.zee5app.service.impl.LoginServiceImpl;
import com.zee.zee5app.service.impl.UserServiceImpl;
import com.zee.zee5app.utils.PasswordUtils;

public class SampleOne {

	static Scanner hs = new Scanner(System.in);

	public static void main(String[] args) {
		UserService userService = null;
		LoginService loginService = null;
		try {
			userService = UserServiceImpl.getInstance();
			loginService = LoginServiceImpl.getInstance();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// adding a record
		System.out.print("Adding ZEE0000006: ");
		try {
			Register register = new Register("ZEE0000006", "Ramesh", "Mahendran", "ramesh@gmail.com", "9275674829",
					"ramesh@2000");
			System.out.println(userService.addUser(register));

		} catch (InvalidNameException | InvalidIdLengthException | InvalidEmailFormatException
				| InvalidPasswordException e) {
			e.printStackTrace();
		}
		// fetch a record by id
		System.out.print("Fetching ZEE0000006: ");
		try {
			Optional<Register> register = userService.getUserById("ZEE0000006");

			if (register.isEmpty())
				System.out.println("Not found");
			else
				System.out.println("found");
		} catch (InvalidNameException | InvalidIdLengthException | InvalidEmailFormatException
				| InvalidPasswordException | IdNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}

		// update a register by id
		System.out.print("Updating ZEE0000006: ");
		try {
			Register register = new Register("ZEE0000006", "Ramesh", "Mahendran", "mramesh@gmail.com", "9275674000",
					"ramesh@2000");
			System.out.println(userService.updateUserById("ZEE0000006", register));

		} catch (InvalidNameException | InvalidIdLengthException | InvalidEmailFormatException
				| InvalidPasswordException | IdNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}

		// trying to change password
		System.out.print("Updating ramesh@gmail.com password: ");
		System.out.println(loginService.changePassword("ramesh@gmail.com",
				PasswordUtils.generateSecurePassword("newPass", PasswordUtils.getSalt(30))));
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}

		// trying to update role
		System.out.print("Updating ramesh@gmail.com role: ");
		System.out.println("Enter 0 for user and any number for admin:");
		int role = hs.nextInt();
		System.out.println(loginService.changeRole("ramesh@gmail.com", ROLE.values()[(role == 0) ? 0 : 1]));
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}

		// delete a record by id
		System.out.print("Deleting ZEE0000006: ");
		try {
			String status = userService.deleteUserById("ZEE0000006");
			System.out.println(status);
		} catch (IdNotFoundException e1) {
			e1.printStackTrace();
		}

		// Get all registers data as a list
		System.out.println("Register List: ");
		try {

			Optional<List<Register>> registers = userService.getAllUsersList();
			if (registers.isPresent())
				registers.get().forEach((register) -> System.out.println(register));
			else
				System.out.println("Empty records.");

		} catch (InvalidNameException | InvalidIdLengthException | InvalidEmailFormatException
				| InvalidPasswordException | IdNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Get all registers data as an array
		System.out.println("Register Array: ");
		try {
			Register[] registers = userService.getAllUsers();
			if ((registers != null) || (registers.length != 0))
				for (Register register : registers)
					System.out.println(register);
			else
				System.out.println("Empty records.");

		} catch (InvalidNameException | InvalidIdLengthException | InvalidEmailFormatException
				| InvalidPasswordException | IdNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
