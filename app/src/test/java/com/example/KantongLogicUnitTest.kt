package com.example

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.ceil

class KantongLogicUnitTest {

    data class MockWallet(val id: String, val name: String, val base: Long)
    data class MockGoal(val id: String, val name: String, val target: Long, val base: Long)
    data class MockTx(
        val id: String,
        val type: String, // "in", "out", "save", "unsave", "tf"
        val amount: Long,
        val walletId: String,
        val toWalletId: String? = null,
        val goalId: String? = null
    )

    private fun calculateWalletBalance(wallet: MockWallet, transactions: List<MockTx>): Long {
        var balance = wallet.base
        for (tx in transactions) {
            if (tx.walletId == wallet.id) {
                when (tx.type) {
                    "in", "unsave" -> balance += tx.amount
                    "out", "save", "tf" -> balance -= tx.amount
                }
            }
            if (tx.type == "tf" && tx.toWalletId == wallet.id) {
                balance += tx.amount
            }
        }
        return balance
    }

    private fun calculateGoalSaved(goal: MockGoal, transactions: List<MockTx>): Long {
        var saved = goal.base
        for (tx in transactions) {
            if (tx.goalId == goal.id) {
                when (tx.type) {
                    "save" -> saved += tx.amount
                    "unsave" -> saved -= tx.amount
                }
            }
        }
        return saved
    }

    @Test
    fun testIncomeAndExpenseBalanceCalculation() {
        val wallet = MockWallet("w1", "Tunai", 100_000L)
        val txs = listOf(
            MockTx("1", "in", 500_000L, wallet.id),
            MockTx("2", "out", 150_000L, wallet.id),
            MockTx("3", "out", 50_000L, wallet.id)
        )
        val balance = calculateWalletBalance(wallet, txs)
        assertEquals(400_000L, balance)
    }

    @Test
    fun testWalletTransferLogic() {
        val walletA = MockWallet("w1", "Bank", 1_000_000L)
        val walletB = MockWallet("w2", "E-Wallet", 50_000L)
        val transferTx = listOf(
            MockTx("1", "tf", 200_000L, walletA.id, toWalletId = walletB.id)
        )

        val balanceA = calculateWalletBalance(walletA, transferTx)
        val balanceB = calculateWalletBalance(walletB, transferTx)

        assertEquals(800_000L, balanceA)
        assertEquals(250_000L, balanceB)
        assertEquals(1_050_000L, balanceA + balanceB) // Conservation of funds
    }

    @Test
    fun testSavingsJarDepositAndWithdrawal() {
        val wallet = MockWallet("w1", "Bank", 2_000_000L)
        val goal = MockGoal("g1", "Dana Darurat", target = 10_000_000L, base = 1_000_000L)

        val txs = listOf(
            MockTx("1", "save", 500_000L, wallet.id, goalId = goal.id),
            MockTx("2", "unsave", 100_000L, wallet.id, goalId = goal.id)
        )

        val walletBalance = calculateWalletBalance(wallet, txs)
        val goalSaved = calculateGoalSaved(goal, txs)

        assertEquals(1_600_000L, walletBalance)
        assertEquals(1_400_000L, goalSaved)
    }

    @Test
    fun testDailyBudgetProjectionFormula() {
        val totalBudget = 3_000_000L
        val spentSoFar = 1_200_000L
        val daysRemainingInMonth = 15

        val remainingBudget = totalBudget - spentSoFar
        assertEquals(1_800_000L, remainingBudget)

        val dailyAllowance = remainingBudget / daysRemainingInMonth
        assertEquals(120_000L, dailyAllowance)
    }

    @Test
    fun testGoalRemainingContributionCalculator() {
        val target = 5_000_000.0
        val current = 2_000_000.0
        val remaining = target - current
        val dailyContribution = 50_000.0

        val daysRequired = ceil(remaining / dailyContribution).toInt()
        assertEquals(60, daysRequired)

        val weeklyContribution = 250_000.0
        val weeksRequired = ceil(remaining / weeklyContribution).toInt()
        assertEquals(12, weeksRequired)

        assertTrue("Calculation should be strictly positive", daysRequired > 0)
    }
}
