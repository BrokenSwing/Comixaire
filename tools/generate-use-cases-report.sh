if [ ! -e "tmp" ] 
then
    mkdir tmp
fi
if [ ! -e "output" ]
then
    mkdir output
fi
cp -R ./assets/. ./tmp/
find ./specifications/usecases/ -type f \( ! -iname "*md" -and ! -iname "*puml" \) -exec cp {} ./tmp/ \;
find ./specifications/usecases/ -type f \( -iname "*md" \) -exec sed "s/[0-9].\([0-9].\?\)* //g" {} >> ./tmp/reports-fragments/use-cases-intro.md \;
cd ./tmp
cat ./reports-fragments/use-cases-outro.md >> ./reports-fragments/use-cases-intro.md
pandoc ./reports-fragments/use-cases-intro.md \
    -f markdown+yaml_metadata_block \
    --template eisvogel \
    --table-of-contents \
    --toc-depth 6 \
    --number-sections \
    --top-level-division=chapter \
    --highlight-style breezedark \
    -o ../output/use-cases-report.pdf
cd ..
rm -rf ./tmp