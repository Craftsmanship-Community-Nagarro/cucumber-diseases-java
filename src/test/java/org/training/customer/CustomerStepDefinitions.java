package org.training.customer;

import static org.assertj.core.api.Assertions.catchThrowable;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;

public class CustomerStepDefinitions {

  private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.of(1995, 1, 1);

  private final CustomerService customerService;
  private LocalDate birthday;
  private String firstName;
  private String lastName;
  private String secondLastName;
  private String secondFirstName;
  private Exception error;
  private int count;

  public CustomerStepDefinitions(CustomerService customerService) {
    this.customerService = customerService;
    this.birthday = DEFAULT_BIRTHDAY;
  }

  @Given("the customer's birthday is {}")
  public void theCustomersBirthdayIs(String date) {
    this.birthday = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
  }

  @Given("the customer name is {} {}")
  public void theCustomerNameIs(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Given("the second customer is {} {}")
  public void theSecondCustomerIs(String firstName, String lastName) {
    this.secondFirstName = firstName;
    this.secondLastName = lastName;
  }

  @When("the customer is created")
  @When("an invalid customer is created")
  public void theCustomerIsCreated() {
    try {
      customerService.addCustomer(firstName, lastName, this.birthday);
    } catch (IllegalArgumentException e) {
      error = e;
    }
  }

  @When("the second customer is created")
  public void theSecondCustomerIsCreated() {
  }

  @Then("the customer creation should be successful")
  public void theCustomerCreationShouldBeSuccessful() {
    Assertions.assertThat(error).isNull();
  }

  @Then("the customer creation should fail")
  public void theCustomerCreationShouldFail() {
    Assertions.assertThat(error).isNotNull();
    Assertions.assertThat(error).hasMessage("Mandatory name parameter is missing");
  }

  @Then("the second customer creation should fail")
  public void theSecondCustomerCreationShouldFail() {
    Throwable error = catchThrowable(() -> customerService.addCustomer(secondFirstName, secondLastName, this.birthday));

    Assertions.assertThat(error).isNotNull().hasMessage("Customer already exists");
  }

  @Given("there are no customers")
  public void thereAreNoCustomers() {
  }

  @Given("no customers exist")
  public void noCustomersExist() {
  }

  @Given("there is a customer")
  public void thereIsACustomer(DataTable customerTable) {
    List<List<String>> row = customerTable.asLists(String.class);

    customerService.addCustomer(row.get(0).get(0), row.get(0).get(1), this.birthday);
  }

  @Given("there are some customers")
  public void thereAreSomeCustomers(DataTable customerTable) {
    List<Map<String, String>> rows = customerTable.asMaps(String.class, String.class);

    for (Map<String, String> col : rows) {
      customerService.addCustomer(col.get("firstname"), col.get("lastname"), this.birthday);
    }
  }

  @When("all customers are searched")
  public void allCustomersAreSearched() {
    count = customerService.searchCustomers().size();
  }

  @When("the customer Sabine Mustermann is searched")
  public void theCustomerSabineMustermannIsSearched() {
    count = customerService.searchCustomers("Sabine", "Mustermann").size();
  }

  @When("the customer Rose Smith is searched")
  public void theCustomerRoseSmithIsSearched() {
  }

  @Then("the customer can be found")
  public void theCustomerCanBeFound() {
    Customer customer = customerService.searchCustomer(firstName, lastName);

    Assertions.assertThat(customer).isNotNull();
  }

  @Then("the customer can not be found")
  public void theCustomerCanNotBeFound() {
    var customer = customerService.searchCustomer(firstName, lastName);

    Assertions.assertThat(customer).isNull();
  }

  @Then("the customer Sabine Mustermann can be found")
  public void theCustomerSabineMustermannCanBeFound() {
    var customer = customerService.searchCustomer("Sabine", "Mustermann");

    Assertions.assertThat(customer.firstName).isEqualTo("Sabine");
    Assertions.assertThat(customer.lastName).isEqualTo("Mustermann");
  }

  @Then("the second customer can be found")
  public void theSecondCustomerCanBeFound() {
    customerService.addCustomer(secondFirstName, secondLastName, this.birthday);
    var customer = customerService.searchCustomer(secondFirstName, secondLastName);

    Assertions.assertThat(customer.firstName).isEqualTo(secondFirstName);
    Assertions.assertThat(customer.lastName).isEqualTo(secondLastName);
  }

  @Then("the number of customers found is {int}")
  public void theNumberOfCustomersFoundIs(int expectedCount) {
    Assertions.assertThat(count).isEqualTo(expectedCount);
  }
}
