.DEFAULT_GOAL := report

.PHONY:report

report:
		allure generate app/build/allure-results --report-dir app/reports/allure --clean