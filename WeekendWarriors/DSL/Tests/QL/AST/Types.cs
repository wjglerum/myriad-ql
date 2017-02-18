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
        public ASTFactory formFactory;

        public INode TypeFromTestCase(string testCase)
        {
            var parser = formFactory.CreateParser(testCase);
            return formFactory.CreateQLObject(parser, ASTFactory.QLObjectType.Form);
        }

        [TestInitialize]
        public void SetupTestFactory()
        {
            formFactory = new ASTFactory();
        }

        [TestMethod]
        public void Form()
        {
            Dictionary<string, string> positiveTestCases = new Dictionary<string, string>
            {
                { "form taxOffice {}", "Test failure: TaxOffice" },
                { "form form1 {}", "Test failure: Identifier form1" }
            };

            foreach (var testCase in positiveTestCases)
            {
                Assert.IsInstanceOfType(
                    TypeFromTestCase(testCase.Key),
                    typeof(QLForm), testCase.Value
                );
            }

            Dictionary<string, string> negativeTestCases = new Dictionary<string, string>
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
                        TypeFromTestCase(testCase.Key),
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
