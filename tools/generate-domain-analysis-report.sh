if [ ! -e "tmp" ] 
then
    mkdir tmp
fi
if [ ! -e "output" ]
then
    mkdir output
fi
cp -R ./assets/. ./tmp/
find ./specifications/domain-analysis/ -type f \( ! -iname "*md" -and ! -iname "*puml" \) -exec cp {} ./tmp/ \;
# find ./specifications/domain-analysis/ -type f \( -iname "*md" \) -exec sed "s/[0-9].\([0-9].\?\)* //g" {} >> ./tmp/reports-fragments/domain-analysis-intro.md \;
cat ./specifications/domain-analysis/class-diagram/README.md >> ./tmp/reports-fragments/domain-analysis-intro.md
cat ./specifications/domain-analysis/booking-activity-diagram/README.md >> ./tmp/reports-fragments/domain-analysis-intro.md
cat ./specifications/domain-analysis/loan-activity-diagram/README.md >> ./tmp/reports-fragments/domain-analysis-intro.md
cat ./specifications/domain-analysis/return-activity-diagram/README.md >> ./tmp/reports-fragments/domain-analysis-intro.md
cd ./tmp
cat ./reports-fragments/domain-analysis-outro.md >> ./reports-fragments/domain-analysis-intro.md
pandoc ./reports-fragments/domain-analysis-intro.md \
    -f markdown+yaml_metadata_block \
    --template eisvogel \
    --table-of-contents \
    --toc-depth 6 \
    --number-sections \
    --top-level-division=chapter \
    --highlight-style breezedark \
    -o ../output/domain-analysis-report.pdf
cd ..
rm -r ./tmp