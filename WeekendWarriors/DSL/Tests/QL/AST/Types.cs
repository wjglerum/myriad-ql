﻿using System;
using System.Text;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using DSL.AST;

namespace Tests.QL.AST
{
    [TestClass]
    public class Types
    {
        public ASTFactory astFactory;

        public INode CreateASTNode(string input, ASTFactory.QLObjectType qlType)
        {
            var parser = astFactory.CreateParser(input);
            return astFactory.CreateQLObject(parser, qlType);
        }

        [TestInitialize]
        public void SetupTestFactory()
        {
            astFactory = new ASTFactory();
        }

        [TestMethod]
        public void Form()
        {
            var positiveTestCases = new Dictionary<string, string>
            {
                { "form taxOffice {}", "Test failure: TaxOffice" },
                { "form form1 {}", "Test failure: Identifier form1" }
            };

            foreach (var testCase in positiveTestCases)
            {
                Assert.IsInstanceOfType(
                    CreateASTNode(testCase.Key, ASTFactory.QLObjectType.Form),
                    typeof(QLForm), testCase.Value
                );
            }

            var negativeTestCases = new Dictionary<string, string>
            {
                { "form \"taxOffice\" {}", "Test failure: TaxOffice quoted" },
                { "form form {}", "Test failure: Double form" },
                { "form taxOffice {", "Test failure: Closing bracket" },
                { "form {}", "Test failure: No identifier" },
                { "form boolean {}", "Test failure: Type identifier" },
                { "form 0abc {}", "Test failure: Number prefix" }
            };

            foreach (var testCase in negativeTestCases)
            {
                try
                {
                    Assert.IsNotInstanceOfType(
                        CreateASTNode(testCase.Key, ASTFactory.QLObjectType.Form),
                        typeof(QLForm), testCase.Value
                    );
                    Assert.Fail(testCase.Value);
                }
                catch (Exception e)
                {
                    // Exception was thrown which means the syntax was incorrect.
                }
            }
        }
    }
}