.DEFAULT_GOAL := report

.PHONY:report ok rg ss

report:
	allure generate app/build/allure-results --report-dir app/reports/allure --clean

ok:
	gradle clean test -PTestngXml=AllOsaid

rg:
	gradle clean test -PTestngXml=AllRishabh

ss:
	gradle clean test -PTestngXml=AllSharukh