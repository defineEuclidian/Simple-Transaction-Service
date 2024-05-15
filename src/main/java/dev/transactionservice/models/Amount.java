package dev.transactionservice.models;

/**
 * Amount class for storing currency, amount of currency, and the type of amount.
 */
public class Amount {
    private String amount = null;
    private String currency = null;
    private DebitCredit debitOrCredit = null;

    /**
     * Default constructor for Amount.
     */
    public Amount() {
      
    }

    /**
     * Parameterized constructor for Amount.
     * 
     * @param amount String
     * @param currency String
     * @param debitOrCredit DebitCredit
     */
    public Amount(String amount, String currency, DebitCredit debitOrCredit) {
      this.amount = amount;
      this.currency = currency;
      this.debitOrCredit = debitOrCredit;
    }

    /**
     * Sets the currency amount.
     * 
     * @param amount String
     */
    public void setAmount(String amount) {
      this.amount = amount;
    }

    /**
     * Sets the currency.
     * 
     * @param currency String
     */
    public void setCurrency(String currency) {
      this.currency = currency;
    }

    /**
     * Sets the type of amount.
     * 
     * @param debitOrCredit DebitCredit
     */
    public void setDebitOrCredit(DebitCredit debitOrCredit) {
      this.debitOrCredit = debitOrCredit;
    }

    /**
     * Gets the currency amount, where the amount in the denomination of the currency. For example, $1 = '1.00'.
     * 
     * @return String object containing the currency amount.
     */
    public String getAmount() {
      return amount;
    }

    /**
     * Gets the currency.
     * 
     * @return String object containing the currency.
     */
    public String getCurrency() {
      return currency;
    }

    /**
     * Gets the type of amount.
     * 
     * @return DebitCredit enumeration as either CREDIT or DEBIT.
     */
    public DebitCredit getDebitOrCredit() {
      return debitOrCredit;
    }

    @Override
    public String toString() {
        return "{\"amount\":\"" + amount + "\",\"currency\":\"" + currency + "\",\"debitOrCredit\":\"" + debitOrCredit + "\"}}";
    }
}
