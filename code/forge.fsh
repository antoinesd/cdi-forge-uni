#  ################################  #
#  Creates the expenses web project  #
#  ################################  #

project-new --named expenses-web --topLevelPackage org.expenses.web ;


jpa-new-entity --named User ;
jpa-new-field --named login ;
jpa-new-field --named password ;
jpa-new-field --named name ;


jpa-new-entity --named Conference ;
jpa-new-field --named name ;
jpa-new-field --named date --type java.util.Date ;
jpa-new-field --named country ;
jpa-new-field --named city ;

java-new-enum --named Currency --targetPackage org.expenses.web.model ;
java-new-enum-const USD ;
java-new-enum-const EURO ;

java-new-enum --named ExpenseType --targetPackage org.expenses.web.model ;
java-new-enum-const HOTEL ;
java-new-enum-const RESTAURANT ;
java-new-enum-const TRAIN ;
java-new-enum-const FLIGHT ;

jpa-new-entity --named Expense ;
jpa-new-field --named description ;
jpa-new-field --named date --type java.util.Date ;
jpa-new-field --named amount --type java.lang.Float ;
jpa-new-field --named expenseType --type org.expenses.web.model.ExpenseType ;
jpa-new-field --named currency --type org.expenses.web.model.Currency ;

jpa-new-entity --named Reimbursement ;
jpa-new-field --named date --type java.util.Date ;
jpa-new-field --named expenses --type org.expenses.web.model.Expense --relationshipType One-to-Many ;
jpa-new-field --named currency --type org.expenses.web.model.Currency ;
jpa-new-field --named user --type org.expenses.web.model.User --relationshipType Many-to-One ;
jpa-new-field --named conference --type org.expenses.web.model.Conference --relationshipType Many-to-One ;


scaffold-generate --provider Faces --targets org.expenses.web.model.* ;

#  #####################################  #
#  Creates the expenses currency project  #
#  #####################################  #

project-new --named expenses-currency --topLevelPackage org.expenses.currency --type jar ;

#  #####################################  #
#  Creates the expenses billing project   #
#  #####################################  #

project-new --named expenses-billing --topLevelPackage org.expenses.billing --type jar ;

#  #####################################  #
#  Creates the expenses banking project   #
#  #####################################  #

project-new --named expenses-banking --topLevelPackage org.expenses.banking --type jar ;
