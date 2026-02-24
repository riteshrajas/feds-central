export const modules = {
    '1': [
        {
            section: "I. Shipping and Packaging of Products",
            question: "What was the total weight (in lbs) of the packages that you transported?",
            hint: "Enter an integer value",
            type: "int",
            min: 0,
            max: 1500,
            eco_tip: "Every lb of package weight contributes to carbon emissions.",
            eco_impact: "high",
            category: "materials"
        },
        {
            section: "I. Shipping and Packaging of Products",
            question: "How many parts did you have to order again on a weekly basis?",
            hint: "Pick the option that best describes your situation",
            type: "multiple-choice",
            options: [
                { label: "A lot (10-15)", eco_impact: "high", value: 3 },
                { label: "A good amount (5-8)", eco_impact: "medium", value: 2 },
                { label: "A little (2-3)", eco_impact: "low", value: 1 },
            ],
            eco_tip: "If you buy without reusing somehow, you are adding more to the pile of waste.",
            category: "materials",
        },
        {
            section: "I. Shipping and Packaging of Products",
            question: "How much do you bulk order during the season?",
            hint: "Pick the option that best describes your situation",
            type: "multiple-choice",
            options: [
                { label: "A good amount", eco_impact: "medium", value: 2 },
                { label: "A little", eco_impact: "low", value: 1 },
            ],
            eco_tip: "Bulk ordering can reduce packaging waste per item.",
            category: "materials",
        },
        {
            section: "I. Shipping and Packaging of Products",
            question: "Where do you order from?",
            hint: "Pick the option that best describes your situation",
            type: "multiple-choice",
            options: [
                { label: "Local", eco_impact: "low", value: 1 },
                { label: "Non-Local", eco_impact: "high", value: 3 },
            ],
            eco_tip: "Ordering locally reduces transportation emissions.",
            category: "materials",
        },
    ],
    '2': [
        {
            section: "II. Disposable Meal Items",
            question: "How many boxes of 50 paper plates did you use?",
            hint: "Pick the option that best describes your situation",
            type: "multiple-choice",
            options: [
                { label: "12-16", eco_impact: "high", value: 3 },
                { label: "5-10", eco_impact: "medium", value: 2 },
                { label: "1-3", eco_impact: "low", value: 1 },
            ],
            eco_tip: "Using reusable plates significantly reduces waste.",
            eco_impact: "medium",
            category: "materials"
        },
        {
            section: "II. Disposable Meal Items",
            question: "How many boxes of 50 plastic forks did you use in an average competition?",
            hint: "Pick the option that best describes your situation",
            type: "multiple-choice",
            options: [
                { label: "10-15", eco_impact: "high", value: 3 },
                { label: "5-8", eco_impact: "medium", value: 2 },
                { label: "1-3", eco_impact: "low", value: 1 },
            ],
            eco_tip: "Reusable silverware is a better eco-friendly option.",
            eco_impact: "medium",
            category: "materials"
        },
        {
            section: "II. Disposable Meal Items",
            question: "How many packets of napkins did you use?",
            type: "multiple-choice",
            options: [
                { label: "5-8", eco_impact: "high", value: 3 },
                { label: "3-5", eco_impact: "medium", value: 2 },
                { label: "1-3", eco_impact: "low", value: 1 },
            ],
            eco_tip: "Consider using cloth napkins or dispensers to reduce waste.",
            eco_impact: "medium",
            category: "materials"
        },
        {
            section: "II. Disposable Meal Items",
            question: "What's your most purchased kind of water bottle pack?",
            type: "multiple-choice",
            options: [
                { label: "Single-use plastic", eco_impact: "high", value: 3 },
                { label: "Large jugs", eco_impact: "medium", value: 2 },
                { label: "Refillable station", eco_impact: "low", value: 1 },
            ],
            eco_tip: "Encourage team members to bring reusable water bottles.",
            eco_impact: "medium",
            category: "materials"
        }, {
            section: "II. Disposable Meal Items",
            question: "How many packs of these water bottles did you use during build seasons and competitions?",
            type: "multiple-choice",
            options: [
                { label: "A lot", eco_impact: "high", value: 3 },
                { label: "A good amount", eco_impact: "medium", value: 2 },
                { label: "A little", eco_impact: "low", value: 1 },
            ],
            eco_tip: "Reducing single-use plastic bottles is a huge step for the environment.",
            eco_impact: "medium",
            category: "materials"
        },
    ],
    '3': [
        {
            section: "III. Mechanical",
            question: "How do you use Drills?",
            hint: "Select an option",
            type: "multiple-choice",
            options: [
                { label: "For Robot", eco_impact: "medium", value: 2 },
                { label: "Own purpose", eco_impact: "medium", value: 2 },
            ],
            eco_tip: "Maintain tools to ensure longevity.",
            eco_impact: "medium",
            category: "energy"
        },
        {
            section: "III. Mechanical",
            question: "How many Drills do you use?",
            hint: "Enter an integer value",
            type: "int",
            min: 0,
            max: 67,
            eco_tip: "Sharing tools can reduce the need for manufacturing new ones.",
            eco_impact: "medium",
            category: "energy"
        },
        {
            section: "III. Programming",
            question: "How do you use Laptops?",
            hint: "Select an option",
            type: "multiple-choice",
            options: [
                { label: "For Robot", eco_impact: "medium", value: 2 },
                { label: "Own purpose", eco_impact: "medium", value: 2 },
            ],
            eco_tip: "Use power-saving modes on laptops when possible.",
            eco_impact: "medium",
            category: "energy"
        },
        {
            section: "III. Programming",
            question: "How many Laptops do you use?",
            hint: "Enter an integer value",
            type: "int",
            min: 0,
            max: 30,
            eco_tip: "Donating old laptops extends their lifecycle.",
            eco_impact: "medium",
            category: "energy"
        },
        {
            section: "III. Electrical",
            question: "How many Batteries/tools do you recharge?",
            hint: "Enter an integer value",
            type: "int",
            min: 0,
            max: 50,
            eco_tip: "Proper battery maintenance extends life and reduces waste.",
            eco_impact: "high",
            category: "energy"
        },
        {
            section: "III. Electrical",
            question: "How often do you charge your batteries?",
            hint: "Select frequency",
            type: "multiple-choice",
            options: [
                { label: "A lot", eco_impact: "high", value: 3 },
                { label: "A good amount", eco_impact: "medium", value: 2 },
                { label: "A little", eco_impact: "low", value: 1 },
            ],
            eco_tip: "Unplug chargers when not in use to save phantom energy.",
            eco_impact: "high",
            category: "energy"
        },
        {
            section: "III. Fabrics",
            question: "How often do you buy filaments?",
            type: "multiple-choice",
            options: [
                { label: "Every day", eco_impact: "high", value: 3 },
                { label: "Once in a week", eco_impact: "medium", value: 2 },
                { label: "Once in a month", eco_impact: "low", value: 1 },
            ],
            eco_tip: "Use biodegradable filaments (like PLA) when possible.",
            eco_impact: "medium",
            category: "materials"
        }
    ],
    '4': [
        {
            section: "IV. Transportation",
            question: "Why do you use transportation?",
            type: "multiple-choice",
            options: [
                { label: "Every day", eco_impact: "high", value: 3 },
                { label: "Once in a week", eco_impact: "medium", value: 2 },
                { label: "Once in a month", eco_impact: "low", value: 1 },
            ],
            eco_tip: "Combine trips to reduce total mileage.",
            eco_impact: "high",
            category: "transport"
        },
        {
            section: "IV. Transportation",
            question: "What kind of transportation do you use?",
            type: "multiple-choice",
            options: [
                { label: "Car (Single Passenger)", eco_impact: "high", value: 3 },
                { label: "Carpool / Public Transport", eco_impact: "medium", value: 2 },
                { label: "Walking / Biking", eco_impact: "low", value: 1 },
            ],
            eco_tip: "Carpooling significantly reduces carbon footprint per person.",
            eco_impact: "high",
            category: "transport"
        }
        , {
            section: "IV. Transportation",
            question: "How many miles do you travel during the season?",
            type: "multiple-choice",
            options: [
                { label: "5000+ miles", eco_impact: "high", value: 3 },
                { label: "500-2500 miles", eco_impact: "medium", value: 2 },
                { label: "0-500 miles", eco_impact: "low", value: 1 },
            ],
            eco_tip: "Plan meetings remotely when possible to save travel.",
            eco_impact: "high",
            category: "transport"
        }

    ]
};
