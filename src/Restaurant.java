import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.Instant;

/**
 * main class for our RRPSS application. Acts as an interface for staff members
 * to interact with.
 */
public class Restaurant {
	
	/**
	 * main runner class for our restaurant app
	 * @param args to be passed in
	 */
	public static void main(String[] args) {
		OrderList restaOrderList = new OrderList();
		TimeHandler handler = new TimeHandler();
		RevenueReport restaReport = new RevenueReport();
		ReservationList restaReserve = new ReservationList();
		TableList restaTable = ObjectReaderWriter.readTableList();
		Menu restaMenu = ObjectReaderWriter.readMenu();
		StaffList restaStaffList = ObjectReaderWriter.readStaffList();
		MemberList restaMember = ObjectReaderWriter.readMemberList();

		int option;
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("SELECT OPTION: ");
			System.out.println("1. Create/Update/Remove menu item\n" + "2. Create/Update/Remove promotion\n"
					+ "3. View Menu\n" + "4. Create order\n" + "5. View order\n"
					+ "6. Add/Remove order item/s to/from order\n" + "7. Create reservation booking\n"
					+ "8. Check reservation booking\n" + "9. Remove reservation booking\n"
					+ "10. Check table availability\n" + "11. Assign customer to table\n" + "12. Print order invoice\n"
					+ "13. Print sale revenue report by period (eg day or month)\n" + "14. Advance time\n"
					+ "15. Show staff list\n" + "16. Add new member\n" + "17. Exit");
			System.out.println("Current Timing: " + handler.getInstant());
			option = sc.nextInt();

			// troubleshooting
			while (option < 0 || option > 17) {
				System.out.println("Invalid option selected. Re-enter: ");
				option = sc.nextInt();
			}
			// exit app
			if (option == 17) {
				System.out.println("Exiting...");
				break;
			}

			switch (option) {

			case 1: // Create/Update/Remove menu item
				int menuOption, choizes;
				String name, description, newfoodname;
				int foodID, foodIndex;
				float price;
				foodType userFoodType;

				while (true) {
					System.out.println("");
					System.out.println("SELECT OPTION: ");
					System.out.println("1. Create Ala Carte Item");
					System.out.println("2. Update Ala Carte Item");
					System.out.println("3. Remove Ala Carte Item");
					System.out.println("4. Print Ala Carte item");
					System.out.println("5. Go back");

					menuOption = sc.nextInt();

					while (menuOption < 0 || menuOption > 5) {
						System.out.println("Invalid option selected. Re-enter: ");
						menuOption = sc.nextInt();
					}
					if (menuOption == 5) {
						System.out.println("Returning to previous option...");
						break;
					}

					switch (menuOption) {
					case 1:
						// System.out.println("\n");
						Boolean actual = true;
						String retain;
						int sortFoodID = 0, foodRealID = 0;
						System.out.println("Enter Food Name to create:");
						sc.nextLine(); // Buffer
						name = sc.nextLine();
						for (int i = 0; i < restaMenu.getAlaCarteList().size(); i++) {
							if (name.equals(restaMenu.getAlaCarteList().get(i).getName())) {
								System.out.println("");
								System.out.println("Current Food Info in list is set as: ");
								System.out.println("Name: " + restaMenu.getAlaCarteList().get(i).getName());
								System.out.printf("Price: $%.2f", restaMenu.getAlaCarteList().get(i).getPrice());
								System.out.println(
										"\nDescription: " + restaMenu.getAlaCarteList().get(i).getDescription());
								System.out.println("Food Type: " + restaMenu.getAlaCarteList().get(i).getFoodType());
								System.out.println("Do you want to retain the previous information? (Y/N):");
								retain = sc.nextLine();
								if (retain.equals("Y")) {
									restaMenu.getAlaCarteList().get(i).setAvailability(true);
									System.out.println("Previous information has been retained.");
									actual = false;
									break;
								} else if (retain.equals("N")) {
									System.out.println("Enter the new price:");
									price = sc.nextFloat();
									restaMenu.getAlaCarteList().get(i).setPrice(price);
									System.out.println("Enter the new description:");
									sc.nextLine(); // Buffer
									description = sc.nextLine();
									restaMenu.getAlaCarteList().get(i).setDescription(description);
									restaMenu.getAlaCarteList().get(i).setAvailability(true);
									System.out.println("Successfully updated!");
									actual = false;
									break;
								}

							}
						}

						if (actual) {
							System.out.println("Enter Price: ");
							price = sc.nextFloat();
							System.out.println("Enter Food Description:");
							sc.nextLine(); // Buffer
							description = sc.nextLine();
							System.out.println("Enter Food Type (Main,Dessert,Drinks,Appetizer):");
							userFoodType = foodType.valueOf(sc.nextLine());

							if (userFoodType == foodType.Main) {
								sortFoodID = 2000;
							} else if (userFoodType == foodType.Drinks) {
								sortFoodID = 3000;
							} else if (userFoodType == foodType.Appetizer) {
								sortFoodID = 4000;
							} else if (userFoodType == foodType.Appetizer) {
								sortFoodID = 5000;
							}

							for (int i = 0; i < restaMenu.getAlaCarteList().size(); i++) {
								if (restaMenu.getAlaCarteList().get(i).getFoodID() < sortFoodID) {
									foodRealID = restaMenu.getAlaCarteList().get(i).getFoodID();
								}
							}

							foodID = foodRealID + 1;

							AlaCarteItem newItem = new AlaCarteItem(name, price, description, userFoodType, foodID,
									true);
							restaMenu.getAlaCarteList().add(newItem);

							// for (int index = 1; index < restaMenu.getAlaCarteList().size(); ++index) {
							// int key = restaMenu.getAlaCarteList().get(index).getFoodID();
							// int position = index - 1;
							// // Shift larger values to the right
							// while (position >= 0 && restaMenu.getAlaCarteList().get(position).getFoodID()
							// > key) {
							// restaMenu.getAlaCarteList().set(index +
							// 1,restaMenu.getAlaCarteList().get(position));
							// position--;
							// }
							// restaMenu.getAlaCarteList().set(position + 1,
							// restaMenu.getAlaCarteList().get(index));
							// }

							System.out.printf(name + " of price $%.2f has been added into food type " + userFoodType,
									price);
							System.out.println("");
						}
						break;
					case 2:
						// System.out.println("\n");
						for (int i = 0; i < restaMenu.getAlaCarteList().size(); i++) {
							if (restaMenu.getAlaCarteList().get(i).getAvailability()) {
								System.out.println("FoodID: " + restaMenu.getAlaCarteList().get(i).getFoodID()
										+ " Name: " + restaMenu.getAlaCarteList().get(i).getName());
							}
						}
						Boolean foodIDvalid = true;
						System.out.println("Enter Food ID to update: ");
						foodID = sc.nextInt();
						while (foodIDvalid) {
							for (int i = 0; i < restaMenu.getAlaCarteList().size(); i++) {
								if (restaMenu.getAlaCarteList().get(i).getFoodID() == foodID) {
									System.out.println("Enter the information to update:");
									System.out.println("1. Name");
									System.out.println("2. Price");
									System.out.println("3. Description");
									System.out.println("4. Food Type");
									choizes = sc.nextInt();

									if (choizes == 1) {
										System.out.println(
												"Current Name is: " + restaMenu.getAlaCarteList().get(i).getName());
										System.out.println("Enter the new name:");
										sc.nextLine();
										newfoodname = sc.nextLine();
										restaMenu.getAlaCarteList().get(i).setName(newfoodname);
										System.out.println("Successfully updated Food Name!");
									} else if (choizes == 2) {
										System.out.printf("Current Price is $%.2f",
												restaMenu.getAlaCarteList().get(i).getPrice());
										System.out.println("\nEnter the new price:");
										price = sc.nextFloat();
										restaMenu.getAlaCarteList().get(i).setPrice(price);
										System.out.println("Successfully updated Food Price!");
									} else if (choizes == 3) {
										System.out.println("Current Description is: "
												+ restaMenu.getAlaCarteList().get(i).getDescription());
										System.out.println("Enter the new description:");
										sc.nextLine();
										description = sc.nextLine();
										restaMenu.getAlaCarteList().get(i).setDescription(description);
										System.out.println("Successfully updated Food Description!");
									} else if (choizes == 4) {
										System.out.println("Current Food Type is: "
												+ restaMenu.getAlaCarteList().get(i).getFoodType());
										System.out.println("Enter the new food type (Main,Dessert,Drinks,Appetizer):");
										sc.nextLine();
										userFoodType = foodType.valueOf(sc.nextLine());
										restaMenu.getAlaCarteList().get(i).setFoodType(userFoodType);
										System.out.println("Successfully updated Food Type!");
									}

									foodIDvalid = false;
									break;
								}
							}

							if (foodIDvalid) {
								System.out.println("Enter valid Food ID to update: ");
								foodID = sc.nextInt();
							}
						}
						break;
					case 3:
						for (int i = 0; i < restaMenu.getAlaCarteList().size(); i++) {
							if (restaMenu.getAlaCarteList().get(i).getAvailability()) {
								System.out.println("FoodID: " + restaMenu.getAlaCarteList().get(i).getFoodID()
										+ " Name: " + restaMenu.getAlaCarteList().get(i).getName());
							}
						}
						Boolean foodavail = true;
						System.out.println("Enter Food ID to remove: ");
						foodID = sc.nextInt();

						while (foodavail) {
							for (int i = 0; i < restaMenu.getAlaCarteList().size(); i++) {
								if (restaMenu.getAlaCarteList().get(i).getFoodID() == foodID
										&& restaMenu.getAlaCarteList().get(i).getAvailability() == true) {
									restaMenu.getAlaCarteList().get(i).setAvailability(false);
									System.out.println("Successfully set food to currently unavailable!");
									foodavail = false;
									break;
								}
							}
							if (foodavail) {
								System.out.println("Enter valid Food ID to update: ");
								foodID = sc.nextInt();
							}
						}
						break;
					case 4:
						boolean noFoodID = true;
						System.out.println("\n");
						for (int i = 0; i < restaMenu.getAlaCarteList().size(); i++) {
							if (restaMenu.getAlaCarteList().get(i).getAvailability() == true) {
								System.out.println("FoodID: " + restaMenu.getAlaCarteList().get(i).getFoodID()
										+ " Name: " + restaMenu.getAlaCarteList().get(i).getName());
							}
						}
						System.out.println("Enter Food ID to check details: ");
						foodID = sc.nextInt();
						for (int i = 0; i < restaMenu.getAlaCarteList().size(); i++) {
							if (foodID == restaMenu.getAlaCarteList().get(i).getFoodID()
									&& restaMenu.getAlaCarteList().get(i).getAvailability() == true) {
								System.out.println("\n");
								System.out.println("Current Food Info in list is set as: ");
								System.out.println("Name: " + restaMenu.getAlaCarteList().get(i).getName());
								System.out.printf("Price: $%.2f", restaMenu.getAlaCarteList().get(i).getPrice());
								System.out.println(
										"\nDescription: " + restaMenu.getAlaCarteList().get(i).getDescription());
								System.out.println("Food Type: " + restaMenu.getAlaCarteList().get(i).getFoodType());
								System.out.println("Food is currently available in Restaurant.");
								noFoodID = false;
								break;
							}
						}
						if (noFoodID) {
							System.out.println("No FoodID " + foodID + " found in list!");
							System.out.println("Exiting ...");
							break;
						}
					}
				}
				break;

			case 2: // Create/Update/Remove promotion
				System.out.println("");
				String tempsetname, tempdes;
				double tempprice;
				String tempcont;
				int tempSetID, tempfoodID;
				boolean loop = true;
				while (loop) {
					System.out.println("Select a option: ");
					System.out.println("1.Create promotion items");
					System.out.println("2.Update promotion set items");
					System.out.println("3.Remove promotion set items");
					System.out.println("4.Display Set on available");
					System.out.println("5.Go back");
					option = sc.nextInt();
					while (option < 0 | option > 5) {
						System.out.println("Invalid option selected. Re-enter: ");
						option = sc.nextInt();
					}
					if (option == 5) {
						System.out.println("Returning to previous option...");
						break;
					}
					switch (option) {
					case 1:
						System.out.println("Set Menu:");
						System.out
								.println("===========================================================================");
						for (int i = 0; i < restaMenu.getSetMenuList().size(); i++) {
							if (restaMenu.getSetMenuList().get(i).getAvailability()) {
								System.out.println(restaMenu.getSetMenuList().get(i).getSetID() + ") Name: "
										+ restaMenu.getSetMenuList().get(i).getName());
								System.out.printf("Price: $%.2f", restaMenu.getSetMenuList().get(i).getPrice());
								System.out.println(
										"\nDescription: " + restaMenu.getSetMenuList().get(i).getDescription());
								System.out.print("Contains: ");
								for (int j = 0; j < restaMenu.getSetMenuList().get(i).getAlaCarteMenuList()
										.size(); j++) {
									System.out.print(
											restaMenu.getSetMenuList().get(i).getAlaCarteMenuList().get(j).getName()
													+ " , ");
								}
								System.out.println("");
								System.out.println(
										"============================================================================");
							}
						}

						// Create New Set Promotion
						sc.nextLine();
						System.out.println("Enter new Set Name:");
						tempsetname = sc.nextLine();

						System.out.println("Enter new Set Price:");
						while (true) {
							try {
								tempprice = sc.nextDouble();
								sc.nextLine();
								break;
							} catch (InputMismatchException e) {
								System.out.println("PLease enter a valid new Set Price:");
								sc.nextLine();
							}
						}

						System.out.println("Enter new Set description:");
						tempdes = sc.nextLine();
						tempSetID = restaMenu.getSetMenuList().size() + 1;

						SetItem temp = new SetItem(tempSetID, tempsetname, tempprice, tempdes);
						restaMenu.getSetMenuList().add(temp);

						System.out.println("Available Alacarte to add into Promotional set: ");
						for (int i = 0; i < restaMenu.getAlaCarteList().size(); i++) {
							if (restaMenu.getAlaCarteList().get(i).getAvailability()) {
								System.out.println("FoodID: " + restaMenu.getAlaCarteList().get(i).getFoodID()
										+ " Name: " + restaMenu.getAlaCarteList().get(i).getName());
							}
						}
						System.out.println("Enter Alacarte Food FoodID to Add into promotional set:");
						System.out.println("Enter -1 to stop adding!");
						tempfoodID = sc.nextInt();
						sc.nextLine();

						while (tempfoodID > 0) {
							boolean found = false;
							if (tempfoodID < 0) {
								break;
							}
							for (int i = 0; i < restaMenu.getAlaCarteList().size(); i++) {
								if (restaMenu.getAlaCarteList().get(i).getFoodID() == tempfoodID) {
									temp.addItem(restaMenu.getAlaCarteList().get(i));
									found = true;
									tempfoodID = 1;
									System.out.println(
											restaMenu.getAlaCarteList().get(i).getName() + " alacarte item added");
									break;
								}
							}
							if (!found) {
								System.out.println("Invalid FoodID! Enter a valid foodID :");
								tempfoodID = sc.nextInt();
								sc.nextLine();
								for (int i = 0; i < restaMenu.getAlaCarteList().size(); i++) {
									if (restaMenu.getAlaCarteList().get(i).getFoodID() == tempfoodID) {
										temp.addItem(restaMenu.getAlaCarteList().get(i));
										found = true;
										tempfoodID = 1;
										System.out.println(
												restaMenu.getAlaCarteList().get(i).getName() + " alacarte item added");
										break;
									}
								}
							}
							tempfoodID = sc.nextInt();
						}

						break;
					case 2:
						// Update exisitng Set promotion
						System.out.println("Set Menu:");
						System.out
								.println("===========================================================================");
						for (int i = 0; i < restaMenu.getSetMenuList().size(); i++) {
							if (restaMenu.getSetMenuList().get(i).getAvailability()) {
								System.out.println(restaMenu.getSetMenuList().get(i).getSetID() + ") Name: "
										+ restaMenu.getSetMenuList().get(i).getName());
								System.out.printf("Price: $%.2f ", restaMenu.getSetMenuList().get(i).getPrice());
								System.out.println(
										"\nDescription: " + restaMenu.getSetMenuList().get(i).getDescription());
								System.out.print("Contains: ");
								for (int j = 0; j < restaMenu.getSetMenuList().get(i).getAlaCarteMenuList()
										.size(); j++) {

									if (restaMenu.getSetMenuList().get(i).getAlaCarteMenuList().size() - 1 == j) {
										System.out.print(restaMenu.getSetMenuList().get(i).getAlaCarteMenuList().get(j)
												.getName());
									} else {
										System.out.print(
												restaMenu.getSetMenuList().get(i).getAlaCarteMenuList().get(j).getName()
														+ " , ");
									}
								}
								System.out.println("");
								System.out.println(
										"============================================================================");
							}
						}
						System.out.println("Enter SetID to Update:");
						while (true) {
							try {
								tempSetID = sc.nextInt();
								sc.nextLine();
								break;
							} catch (InputMismatchException e) {
								System.out.println("PLease enter a valid new Set Price:");
								sc.nextLine();
							}
						}

						boolean exist = false;
						for (int i = 0; i < restaMenu.getSetMenuList().size(); i++) {
							if (restaMenu.getSetMenuList().get(i).getSetID() == tempSetID) {

								System.out.println("Enter new name:");
								tempsetname = sc.nextLine();

								restaMenu.getSetMenuList().get(i).setName(tempsetname);

								System.out.println("Enter new price:");
								while (true) {
									try {
										tempprice = sc.nextDouble();
										sc.nextLine();
										restaMenu.getSetMenuList().get(i).setPrice(tempprice);
										exist = true;
										break;
									} catch (InputMismatchException e) {
										System.out.println("PLease enter a valid new Set Price:");
										sc.nextLine();
									}
								}
								System.out.println("SetID: " + restaMenu.getSetMenuList().get(i).getSetID());
								System.out.println("Set name: " + restaMenu.getSetMenuList().get(i).getName());
								System.out.printf("Promotion set price: $%.2f ",
										restaMenu.getSetMenuList().get(i).getPrice());
								System.out.println("");
								System.out.print("\nSet contains: ");
								for (int j = 0; j < restaMenu.getSetMenuList().get(i).getAlaCarteMenuList()
										.size(); j++) {
									if (restaMenu.getSetMenuList().get(i).getAlaCarteMenuList().size() - 1 == j) {
										System.out.print(restaMenu.getSetMenuList().get(i).getAlaCarteMenuList().get(j)
												.getName());
									} else {
										System.out.print(
												restaMenu.getSetMenuList().get(i).getAlaCarteMenuList().get(j).getName()
														+ " , ");
									}
								}
								System.out.println("");
								System.out.println(
										"============================================================================");
								System.out.println("1) Add new Alacarte Item to exisiting SetID");
								System.out.println("2) Remove existing Alacarte Item to exisiting SetID ");
								System.out.println("3) Save changes to new set name,set price and set description");
								System.out.println("Enter your option number: ");
								option = sc.nextInt();
								temp = restaMenu.getSetMenuList().get(i);
								switch (option) {
								case 1:
									System.out.println("Available Alacarte to add into Promotional set: ");
									for (int e = 0; e < restaMenu.getAlaCarteList().size(); e++) {
										if (restaMenu.getAlaCarteList().get(e).getAvailability()) {
											System.out
													.println("FoodID: " + restaMenu.getAlaCarteList().get(e).getFoodID()
															+ " Name: " + restaMenu.getAlaCarteList().get(e).getName());
										}
									}
									System.out.println("Enter Alacarte Food FoodID to Add into promotional set:");
									System.out.println("Enter -1 to stop adding!");
									tempfoodID = sc.nextInt();
									sc.nextLine();

									while (tempfoodID > 0) {
										boolean found = false;
										if (tempfoodID < 0) {
											break;
										}
										for (int f = 0; f < restaMenu.getAlaCarteList().size(); f++) {
											if (restaMenu.getAlaCarteList().get(f).getFoodID() == tempfoodID) {
												temp.addItem(restaMenu.getAlaCarteList().get(f));
												found = true;
												tempfoodID = 1;
												System.out.println(restaMenu.getAlaCarteList().get(f).getName()
														+ " alacarte item added");
												break;
											}
										}
										if (!found) {
											System.out.println("Invalid FoodID! Enter a valid foodID :");
											tempfoodID = sc.nextInt();
											sc.nextLine();
											for (int g = 0; g < restaMenu.getAlaCarteList().size(); g++) {
												if (restaMenu.getAlaCarteList().get(g).getFoodID() == tempfoodID) {
													temp.addItem(restaMenu.getAlaCarteList().get(g));
													found = true;
													tempfoodID = 1;
													System.out.println(restaMenu.getAlaCarteList().get(g).getName()
															+ " alacarte item added");
													break;
												}
											}
										}
										tempfoodID = sc.nextInt();
									}
									break;
								case 2:
									System.out.println("Available Alacarte to delete into Promotional set: ");
									for (int e = 0; e < temp.getAlaCarteMenuList().size(); e++) {
										if (temp.getAlaCarteMenuList().get(e).getAvailability()) {
											System.out
													.println("FoodID: " + temp.getAlaCarteMenuList().get(e).getFoodID()
															+ " Name: " + temp.getAlaCarteMenuList().get(e).getName());
										}
									}
									System.out.println("Enter Alacarte Food FoodID to delete into promotional set:");
									System.out.println("Enter -1 to stop adding!");
									tempfoodID = sc.nextInt();
									sc.nextLine();

									while (tempfoodID > 0) {
										boolean found = false;
										if (tempfoodID < 0) {
											break;
										} else if (temp.getAlaCarteMenuList().size() == 1) {
											temp.setAvailability(false);
										}
										for (int f = 0; f < temp.getAlaCarteMenuList().size(); f++) {
											if (temp.getAlaCarteMenuList().get(f).getFoodID() == tempfoodID) {
												String tempname = temp.getAlaCarteMenuList().get(f).getName();
												temp.deleteItem(temp.getAlaCarteMenuList().get(f));
												found = true;
												tempfoodID = 1;
												System.out.println(tempname + " alacarte item deleted");
												break;
											}
										}
										if (!found) {
											System.out.println("Invalid FoodID! Enter a valid foodID :");
											tempfoodID = sc.nextInt();
											sc.nextLine();
											for (int g = 0; g < temp.getAlaCarteMenuList().size(); g++) {
												if (temp.getAlaCarteMenuList().get(g).getFoodID() == tempfoodID) {
													temp.deleteItem(temp.getAlaCarteMenuList().get(g));
													found = true;
													tempfoodID = 1;
													System.out.println(temp.getAlaCarteMenuList().get(g).getName()
															+ " alacarte item deleted");
													break;
												}
											}
										}
										tempfoodID = sc.nextInt();
									}
									break;
								case 3:
									System.out.println("Saving set name, set price and set desciption.......");
									break;
								default:
									System.out.println("Invalid FoodID! Enter a valid option :");
									option = sc.nextInt();
								}

								break;
							}
						}
						if (!exist) {
							System.out.println("Enter a valid SetID to update!");
						}
						break;
					case 3:
						System.out.println("Set Menu:");
						System.out
								.println("===========================================================================");
						for (int i = 0; i < restaMenu.getSetMenuList().size(); i++) {
							if (restaMenu.getSetMenuList().get(i).getAvailability()) {
								System.out.println(restaMenu.getSetMenuList().get(i).getSetID() + ") Name: "
										+ restaMenu.getSetMenuList().get(i).getName());
								System.out.printf("Price: $%.2f", restaMenu.getSetMenuList().get(i).getPrice());
								System.out.println(
										"\nDescription: " + restaMenu.getSetMenuList().get(i).getDescription());
								System.out.print("Contains: ");
								for (int j = 0; j < restaMenu.getSetMenuList().get(i).getAlaCarteMenuList()
										.size(); j++) {
									System.out.print(
											restaMenu.getSetMenuList().get(i).getAlaCarteMenuList().get(j).getName()
													+ " , ");
								}
								System.out.println("");
								System.out.println(
										"============================================================================");
							}
						}
						System.out.println("Enter SetID to delete:");
						while (true) {
							try {
								tempSetID = sc.nextInt();
								break;
							} catch (InputMismatchException e) {
								System.out.println("PLease enter a valid new Set Price:");
								sc.nextLine();
							}
						}

						for (int i = 0; i < restaMenu.getSetMenuList().size(); i++) {
							if (restaMenu.getSetMenuList().get(i).getSetID() == tempSetID) {
								restaMenu.getSetMenuList().get(i).setAvailability(false);
								break;
							}
						}
						break;
					case 4:
						System.out.println("Set Menu:");
						System.out
								.println("===========================================================================");
						for (int q = 0; q < restaMenu.getSetMenuList().size(); q++) {
							if (restaMenu.getSetMenuList().get(q).getAvailability()) {
								System.out.println(restaMenu.getSetMenuList().get(q).getSetID() + ") Name: "
										+ restaMenu.getSetMenuList().get(q).getName());
								System.out.printf("Price: $%.2f", restaMenu.getSetMenuList().get(q).getPrice());
								System.out.println(
										"\nDescription: " + restaMenu.getSetMenuList().get(q).getDescription());
								System.out.print("Contains: ");
								for (int j = 0; j < restaMenu.getSetMenuList().get(q).getAlaCarteMenuList()
										.size(); j++) {
									if (restaMenu.getSetMenuList().get(q).getAlaCarteMenuList().size() - 1 == j) {
										System.out.print(restaMenu.getSetMenuList().get(q).getAlaCarteMenuList().get(j)
												.getName());
									} else {
										System.out.print(
												restaMenu.getSetMenuList().get(q).getAlaCarteMenuList().get(j).getName()
														+ " , ");
									}
								}
								System.out.println("");
								System.out.println(
										"============================================================================");
							}
						}
						break;

					}
				}

				break;

			case 3: // View menu
				restaMenu.printer();
				break;

			case 4: // Create order
				System.out.println("Please enter StaffID");
				int staffID4 = sc.nextInt();
				System.out.println("Please enter Table Number: ");
				int tableID4 = sc.nextInt(); // enter value found from assignTable
				System.out.printf("Please pick Order Type\n(1) Dine In\n(2) Takeout\n");
				int choice4 = sc.nextInt();
				orderType type4;

				while (choice4 != 1 && choice4 != 2) {
					System.out.println("Invalid entry. Please re-enter:");
					choice4 = sc.nextInt();
				}
				if (choice4 == 1) {
					type4 = orderType.dine_in;
				} else
					type4 = orderType.takeout;

				Instant time4 = handler.getInstant();
				int toPrintOrderID = restaOrderList.addOrder(staffID4, time4, type4, tableID4);
				System.out.printf("Order ID: %d\n", toPrintOrderID);
				System.out.println("");
				break;

			case 5: // View order
				System.out.println("Please enter orderID");
				int orderID5 = sc.nextInt();
				Order toPrintOrder = restaOrderList.getOrderByOrderID(orderID5);
				if (toPrintOrder != null) {
					toPrintOrder.printList();
				}
				System.out.println("");
				break;

			case 6: // Add/Remove order item/s to/from order
				System.out.println("Please enter orderID");
				int orderID6 = sc.nextInt();
				restaOrderList.updateOrder(orderID6, restaMenu);
				System.out.println("");
				break;

			case 7: // Create reservation booking
				System.out.println("Creating a new reservation");
				int dateInt;
				String day, month, year, hours, minute,timeStr;

				while (true) {
					try {
						System.out.println("Enter Date in the format (DDMMYYYY):"); // truncate D/M/Y
						dateInt = sc.nextInt();
						String dateInput = "" + dateInt;
						day = dateInput.substring(0, 2);
						month = dateInput.substring(2, 4);
						year = dateInput.substring(4, 8);
						System.out.println("Enter Time in the format (HHMM):");
						timeStr = sc.next();
						hours = timeStr.substring(0, 2);
						minute = timeStr.substring(2, 4);
						break;

					} catch (InputMismatchException | StringIndexOutOfBoundsException inputError) {
						System.out.println("Please re-enter in the correct format");
						sc.nextLine();
					}
				}

				System.out.println("Enter no. pax");
				int pax = sc.nextInt();
				while (pax < 1 || pax > 10) {
					System.out.println("Invalid number. Re-enter");
					pax = sc.nextInt();
				}
				System.out.println("Enter phone no.:");
				int phoneNum = sc.nextInt();

				while (String.valueOf(phoneNum).length() != 8) {
					System.out.println("Invalid number. Re-enter");
					phoneNum = sc.nextInt();
				}

				Instant inst1 = Instant.parse(year + "-" + month + "-" + day + "T" + hours + ":" + minute + ":00.00Z");

				// Check if reservation is made in advance//
				Reservation newReserve = new Reservation(inst1, pax, phoneNum);
				if (!newReserve.checkIfInAdvance(handler.getInstant())) {
					System.out.println("Reservation can only be made 1 hour in advance\n");
					break;
				}

				int tables[] = restaTable.matchUpcomingTable(pax);

				restaReserve.checkUpcomingReserved(tables, newReserve);

				// restaReserve.printReservation();
				System.out.println("");
				break;

			case 8: // Check/Remove reservation booking
				while (true) {
					System.out.println("1. View all current bookings\n2. View specific booking (phoneNum)");
					int checkBooking = sc.nextInt();
					try {
						switch (checkBooking) {
						case 1:
							restaReserve.printReservation();
							break;
						case 2:
							System.out.println("Enter the phone number used for reservation");
							int phoneNumBooking = sc.nextInt();
							String length = Integer.toString(phoneNumBooking);
							if (length.length() != 8) {
								throw new InputMismatchException();
							}
							restaReserve.checkReservation(phoneNumBooking);
							break;
						}
					} catch (InputMismatchException inputError) {
						System.out.println("Please re-enter in the correct format");
						sc.nextLine();
					}
					break;
				}
				System.out.println("\n");
				break;
			case 9: // Remove reservation booking
				System.out.println("Enter the phone number used for reservation");
				while (true) {
					try {
						phoneNum = sc.nextInt();
						String length = Integer.toString(phoneNum);
						if (length.length() != 8) {
							throw new InputMismatchException();
						}
						break;

					} catch (InputMismatchException | StringIndexOutOfBoundsException inputError) {
						System.out.println("Please re-enter in the correct format");
						sc.nextLine();
					}
				}
				restaReserve.removeReservation(phoneNum);
				System.out.println(" ");
				break;

			case 10: // Check table availability
				System.out.println("Number of tables available: " + restaTable.getEmpty() + "\n");
				restaTable.printList();
				System.out.println(" ");
				break;

			case 11: // Assign customer to table
				System.out.println("1. Walk-in\n2. Reservation");
				int checkInt = sc.nextInt();
				while (true) {
					try {
						switch (checkInt) {
						case 1:
							System.out.println("Enter number of pax: ");
							int pax11 = sc.nextInt();
							while (pax11 < 1 || pax11 > 10) {
								System.out.println("Invalid number of people. Please re-enter:");
								pax11 = sc.nextInt();
							}
							int[] availableTables11 = restaTable.matchCurrentTable(pax11);
							// get first available table considering reservation list
							int tableID11 = restaReserve.checkCurrentReserved(availableTables11, handler.getInstant());

							if (tableID11 == -1) {
								System.out.println("No available table. Please wait.");
								break;
							}
							restaTable.occupyTable(tableID11);
							System.out.println("Table with ID of " + tableID11 + " occupied!");
							break;
						case 2:
							System.out.println("Enter number used for reservation");
							int phoneNo = sc.nextInt();
							boolean foundReserve = restaReserve.checkReservation(phoneNo);
							if (foundReserve) {
								int tableNum = restaReserve.getTable(phoneNo);
								restaReserve.removeReservation(phoneNo);
								restaTable.occupyTable(tableNum);
							} else {
								System.out.println("Reservation not found");
							}
							break;
						}
					} catch (InputMismatchException inputError) {
						System.out.println("Please re-enter in the correct format");
						sc.nextLine();
					}
					break;
				}
				System.out.println(" ");
				break;
			case 12: // Print order invoice
				System.out.println("Please enter order ID: ");
				int orderID12 = sc.nextInt();
				System.out.println("Please enter Phone number for member verification");
				Boolean memberStat12 = restaMember.checkMember(sc.nextInt()); // check for member or ask for member
				int tableID12 = restaOrderList.getTableIDByOrderID(orderID12);
				System.out.println("\n=========================================");
				System.out.printf("Time: %s\t\tTable %d\n", handler.getInstant(), tableID12);
				restaOrderList.generateInvoice(orderID12, memberStat12);
				restaTable.emptyTable(tableID12);
				restaReport.addToArchive(restaOrderList.getOrderByOrderID(orderID12));
				System.out.println(" ");
				break;

			case 13: // Print sale revenue report by period (eg day or month)
				String restDay, restMonth, restYear, ini, fin, date13;
				System.out.println("Enter Date in the format (DDMMYYYY):");
				date13 = sc.next();
				restDay = date13.substring(0, 2);
				restMonth = date13.substring(2, 4);
				restYear = date13.substring(4, 8);
				ini = restYear + "-" + restMonth + "-" + restDay + "T00:00:00Z";
				System.out.println("Enter Date in the format (DDMMYYYY):");
				date13 = sc.next();
				restDay = date13.substring(0, 2);
				restMonth = date13.substring(2, 4);
				restYear = date13.substring(4, 8);
				fin = restYear + "-" + restMonth + "-" + restDay + "T00:00:00Z";
				restaReport.periodRevenue(Instant.parse(ini), Instant.parse(fin));
				System.out.println(" ");
				break;

			case 14: // Advance time by 1 hour
				System.out.println("Enter how long you want to advance time in minutes");
				int advTiming = sc.nextInt();
				handler.advanceTime(advTiming);

				// for checking if reservation expired
				Instant time = handler.getInstant();
				restaReserve.removeExpired(time);
				System.out.println(" ");
				break;

			case 15: // print staff list
				restaStaffList.printList();
				System.out.println(" ");
				break;

			case 16: // add member to list
				restaMember.add_Person();
				System.out.println(" ");
				break;
			}

		}

		// throw new UnsupportedOperationException();
	}
	// >>>>>>> Stashed changes
}