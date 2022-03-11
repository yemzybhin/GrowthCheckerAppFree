package ade.yemi.growthchecker.Data

class Quotes(val quote: String, val Author: String) {}

fun AllQuotes(): List<Quotes>{
    var allquotes = listOf<Quotes>(
            Quotes("1The very first time i saw the thing i needed, that was the reason for all these things", "1Adam Shimley"),
            Quotes("2The man that did all these things to the loved people of russia is the same person that laments abou things", "2Adam Victor"),
            Quotes("3The man that did all these things to the loved people of russia is the same person that laments abou things", "3Adam Victor"),
            Quotes("4The man that did all these things to the loved people of russia is the same person that laments abou things", "4Adam Victor"),
            Quotes("5The man that did all these things to the loved people of russia is the same person that laments abou things", "5Adam Victor"),
            Quotes("6The man that did all these things to the loved people of russia is the same person that laments abou things", "6Adam Victor"),
            Quotes("7The man that did all these things to the loved people of russia is the same person that laments abou things", "7Adam Victor"),
            Quotes("8The man that did all these things to the loved people of russia is the same person that laments abou things", "8Adam Victor"),
            Quotes("9The man that did all these things to the loved people of russia is the same person that laments abou things", "9Adam Victor"),
            Quotes("10The man that did all these things to the loved people of russia is the same person that laments abou things", "10Adam Victor")
    )
   return allquotes
}